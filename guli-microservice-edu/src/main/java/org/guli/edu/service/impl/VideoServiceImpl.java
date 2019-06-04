package org.guli.edu.service.impl;

import org.guli.edu.entity.Video;
import org.guli.edu.mapper.VideoMapper;
import org.guli.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}
