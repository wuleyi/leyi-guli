package org.guli.edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.guli.edu.entity.Course;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author Leyi
 * @since 2019-06-04
 */
@Repository
public interface CourseMapper extends BaseMapper<Course> {

    @Delete("DELETE course,chapter,video FROM edu_course course LEFT JOIN edu_chapter chapter ON chapter.course_id = course.id LEFT JOIN edu_video video ON video.course_id = course.id WHERE course.id = #{courseId}")
    int removeAllById(String courseId);

}
