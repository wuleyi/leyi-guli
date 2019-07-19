package org.guli.edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.guli.edu.entity.Course;
import org.guli.edu.entity.dto.CourseDetailsInfoDto;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Select("SELECT co.id id,co.title,co.price,co.lesson_num,co.cover,de.description,sub.title subject_parent_title,sup.title subject_title,te.`name` teacher_name,te.avatar teacher_avatar,te.intro teacher_intro,te.career teacher_career,te.`level` teacher_level,ch.id chapter_id,ch.title chapter_title,vi.id video_id,vi.title video_title,vi.is_free video_is_free FROM edu_course co LEFT JOIN edu_course_description de ON de.id = co.id LEFT JOIN edu_teacher te ON te.id = co.teacher_id LEFT JOIN edu_subject sup ON sup.id = co.subject_id LEFT JOIN edu_subject sub ON sub.id = co.subject_parent_id RIGHT JOIN edu_chapter ch ON ch.course_id = co.id RIGHT JOIN edu_video vi ON vi.chapter_id = ch.id WHERE co.id = #{courseId}")
    List<CourseDetailsInfoDto> selectCourseDetailsInfo(String courseId);

}
