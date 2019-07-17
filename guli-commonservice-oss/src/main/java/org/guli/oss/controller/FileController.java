package org.guli.oss.controller;

import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.guli.oss.service.FileService;
import org.guli.oss.util.ConstantPropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api("阿里云OSS管理")
@CrossOrigin
@RestController
@RequestMapping("/admin/oss/file")
public class FileController {

    @Autowired
    FileService fileService;

    @ApiOperation("阿里云文件上传")
    @PostMapping("upload")
    public R upload(@ApiParam(name = "file", value = "文件", required = true)
                    @RequestParam("file") MultipartFile file,
                    @ApiParam(name = "host", value = "文件上传路径")
                    @RequestParam(value = "host", required = false) String host) {

        if(!StringUtils.isEmpty(host))
            ConstantPropertiesUtil.FILE_HOST = host;

        String resultUrl = fileService.upload(file);
        return R.ok(resultUrl).setMsg("文件上传成功");
    }

}
