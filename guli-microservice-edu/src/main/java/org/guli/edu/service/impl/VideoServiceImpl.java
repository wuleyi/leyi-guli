package org.guli.edu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.guli.common.util.Assert;
import org.guli.common.util.GuliUtils;
import org.guli.edu.entity.Video;
import org.guli.edu.entity.form.VideoInfoForm;
import org.guli.edu.mapper.VideoMapper;
import org.guli.edu.service.VideoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author Leyi
 * @since 2019-06-04
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Override
    public void saveVideoInfo(VideoInfoForm videoInfoForm) {

        Assert.isTrue(this.save(GuliUtils.copyPropertiesPlus(videoInfoForm, new Video())), "保存课时失败");
    }

    @Override
    public VideoInfoForm getVideoInfoFormById(String id) {

        Video video = this.getById(id);
        Assert.notNull(video, "根据id查找课时失败");

        return GuliUtils.copyPropertiesPlus(video, new VideoInfoForm());
    }

    @Override
    public void updateVideoInfoById(VideoInfoForm videoInfoForm) {

        Assert.isTrue(this.updateById(GuliUtils.copyPropertiesPlus(videoInfoForm, new Video())), "修改课时失败");
    }

    @Override
    public boolean removeVideoById(String id) {

        // TODO 删除阿里云上的视频资源
        return this.removeById(id);
    }

}
