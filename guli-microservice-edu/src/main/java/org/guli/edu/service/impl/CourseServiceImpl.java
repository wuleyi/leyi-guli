package org.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.guli.common.constant.GuliGlobalConsts;
import org.guli.common.constant.PriceConstants;
import org.guli.common.util.Assert;
import org.guli.common.util.GuliUtils;
import org.guli.edu.entity.Course;
import org.guli.edu.entity.CourseDescription;
import org.guli.edu.entity.form.CourseInfoForm;
import org.guli.edu.entity.query.CourseQuery;
import org.guli.edu.mapper.CourseMapper;
import org.guli.edu.service.CourseDescriptionService;
import org.guli.edu.service.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

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

    @Autowired
    private CourseDescriptionService courseDescriptionService;

    @Override
    @Transactional
    public String saveCourseInfoForm(CourseInfoForm courseInfoForm) {

        Course course = new Course();
        BeanUtils.copyProperties(courseInfoForm, course);
        course.setStatus(GuliGlobalConsts.COURSE_STATUS.DRAFT);

        this.save(course);

        final String courseId = course.getId();
        courseDescriptionService.save(new CourseDescription(courseId, courseInfoForm.getDescription()));

        return courseId;
    }

    @Override
    public CourseInfoForm getCourseInfoFormById(String id) {

        Course course = this.getById(id);
        Assert.notNull(course, "根据课程id查找课程数据不存在");

        CourseInfoForm courseInfoForm = GuliUtils.copyPropertiesPlus(course, new CourseInfoForm());

        CourseDescription courseDescription = courseDescriptionService.getById(id);

        // 防止根据课程id查找描述时抛出异常，因为描述不是必填项
        if (null != courseDescription) courseInfoForm.setDescription(courseDescription.getDescription());

        // 设置显示精度：舍弃多余的位数
        courseInfoForm.setPrice(courseInfoForm.getPrice()
                .setScale(PriceConstants.DISPLAY_SCALE, BigDecimal.ROUND_CEILING));
        return courseInfoForm;
    }

    @Override
    @Transactional
    public void updateCourseInfoById(String id, CourseInfoForm courseInfoForm) {

        //保存课程基本信息
        Assert.isTrue(this.updateById(GuliUtils.copyPropertiesPlus(courseInfoForm, new Course())), "课程信息保存失败");

        //保存课程详情信息
        Assert.isTrue(courseDescriptionService.updateById(new CourseDescription(id, courseInfoForm.getDescription())), "课程详细信息保存失败");
    }

    @Override
    public IPage<Course> pageQuery(Page<Course> pageParam, CourseQuery courseQuery) {

        LambdaQueryWrapper<Course> queryWrapper = new LambdaQueryWrapper<Course>().orderByDesc(Course::getGmtCreate);

        if (courseQuery != null) {
            String title = courseQuery.getTitle();
            String teacherId = courseQuery.getTeacherId();
            String subjectParentId = courseQuery.getSubjectParentId();
            String subjectId = courseQuery.getSubjectId();

            queryWrapper.like(!StringUtils.isEmpty(title), Course::getTitle, title)
                    .eq(!StringUtils.isEmpty(teacherId), Course::getTeacherId, teacherId)
                    .eq(!StringUtils.isEmpty(subjectParentId), Course::getSubjectParentId, subjectParentId)
                    .eq(!StringUtils.isEmpty(subjectId), Course::getSubjectId, subjectId);
        }
        return this.page(pageParam, queryWrapper);
    }

    @Override
    public boolean removeCourseById(String id) {

        return baseMapper.removeAllById(id) > 0;
    }

}
