package org.guli.oss.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import org.guli.common.exception.GuliException;
import org.guli.oss.service.FileService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static org.guli.oss.util.ConstantPropertiesUtil.*;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String upload(MultipartFile file) {
        String uploadedUrl = "";

        try {
            // 创建OSSClient实例。
            OSSClient ossClient = new OSSClient(END_POINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);

            if (!ossClient.doesBucketExist(BUCKET_NAME)) {
                ossClient.createBucket(BUCKET_NAME);
                ossClient.setBucketAcl(BUCKET_NAME, CannedAccessControlList.PublicRead);
            }

            // 上传文件流。
            InputStream inputStream = file.getInputStream();


            String filePath = new DateTime().toString("yyyy/MM/dd");
            String originalName = file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String fileType = originalName.substring(originalName.lastIndexOf("."));
            String newFileName = fileName + fileType;
            String fileUrl = FILE_HOST + "/" + filePath + "/" + newFileName;

            ossClient.putObject(BUCKET_NAME, fileUrl, inputStream);

            uploadedUrl = "https://" + BUCKET_NAME + "." + END_POINT + "/" + fileUrl;

            // 关闭OSSClient。
            ossClient.shutdown();
        } catch (IOException e) {
            throw new GuliException("OSS上传文件失败");
        }

        return uploadedUrl;
    }

}
