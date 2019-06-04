package org.guli.edu.service.impl;

import org.guli.edu.entity.Course;
import org.guli.edu.mapper.CourseMapper;
import org.guli.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Leyi
 * @since 2019-06-04
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

}
