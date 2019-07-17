package org.guli.edu.service;

import org.guli.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;
import org.guli.edu.entity.form.VideoInfoForm;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author Leyi
 * @since 2019-06-04
 */
public interface VideoService extends IService<Video> {

    void saveVideoInfo(VideoInfoForm videoInfoForm);

    VideoInfoForm getVideoInfoFormById(String id);

    void updateVideoInfoById(VideoInfoForm videoInfoForm);

    boolean removeVideoById(String id);

}
