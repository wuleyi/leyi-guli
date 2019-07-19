package org.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.guli.common.constant.GuliGlobalConsts;
import org.guli.common.constant.PriceConstants;
import org.guli.common.util.Assert;
import org.guli.common.util.GuliUtils;
import org.guli.edu.entity.Course;
import org.guli.edu.entity.CourseDescription;
import org.guli.edu.entity.dto.CourseDetailsInfoDto;
import org.guli.edu.entity.dto.CourseDetailsInfoVo;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public CourseDetailsInfoVo listCourseDetailsInfoById(String id) {

        // 原始总数据
        List<CourseDetailsInfoDto> originalDatas = baseMapper.selectCourseDetailsInfo(id);

        // 前置校验，给定课程id没有查询到数据
        Assert.notEmpty(originalDatas, "根据课程编号查询课程信息为空");

        // 返回结果对象
        CourseDetailsInfoVo courseDetailsInfoVo = GuliUtils.copyPropertiesPlus(originalDatas.get(0), new CourseDetailsInfoVo());

        // 根据章节id分组后的map
        Map<String, List<CourseDetailsInfoDto>> chapterMap = originalDatas.stream().collect(Collectors.groupingBy(CourseDetailsInfoDto::getChapterId));

        // 用于存储章节信息，最终传递给返回结果对象
        List<CourseDetailsInfoVo.ChapterDto> chapterDtos = Lists.newArrayList();

        chapterMap.entrySet().forEach(chapterEntrySet -> {

            // 根据章节id分组后map中的val，即章节列表
            List<CourseDetailsInfoDto> chapterList = chapterEntrySet.getValue();

            // 用于存储章节其下的课时信息，最终传递给章节对象
            List<CourseDetailsInfoVo.ChapterDto.VideoDto> videoDtos = Lists.newArrayList();
            chapterDtos.add(new CourseDetailsInfoVo().new ChapterDto(chapterEntrySet.getKey(), chapterList.get(0).getChapterTitle(), videoDtos));

            // 根据视频id分组后的map
            Map<String, List<CourseDetailsInfoDto>> videoMap = chapterList.stream().collect(Collectors.groupingBy(CourseDetailsInfoDto::getVideoId));

            // 同上
            videoMap.entrySet().forEach(videoEntrySet -> videoEntrySet.getValue().forEach(video -> videoDtos.add(GuliUtils.copyPropertiesPlus(video, new CourseDetailsInfoVo().new ChapterDto().new VideoDto()))));
        });
        // 将章节集合传递给返回结果对象
        courseDetailsInfoVo.setChapterDtoList(chapterDtos);
        return courseDetailsInfoVo;
    }

}
