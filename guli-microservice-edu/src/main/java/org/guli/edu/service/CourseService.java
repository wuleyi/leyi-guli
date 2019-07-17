package org.guli.edu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.guli.edu.entity.Course;
import org.guli.edu.entity.form.CourseInfoForm;
import org.guli.edu.entity.query.CourseQuery;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Leyi
 * @since 2019-06-04
 */
public interface CourseService extends IService<Course> {

    String saveCourseInfoForm(CourseInfoForm courseInfoForm);

    CourseInfoForm getCourseInfoFormById(String id);

    void updateCourseInfoById(String id, CourseInfoForm courseInfoForm);

    IPage<Course> pageQuery(Page<Course> pageParam, CourseQuery courseQuery);

    boolean removeCourseById(String id);

}
