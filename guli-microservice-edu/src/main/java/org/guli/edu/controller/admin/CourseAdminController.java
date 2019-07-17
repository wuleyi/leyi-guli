package org.guli.edu.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.guli.edu.entity.Course;
import org.guli.edu.entity.form.CourseInfoForm;
import org.guli.edu.entity.query.CourseQuery;
import org.guli.edu.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Leyi
 * @since 2019-06-04
 */
@Api("课程管理")
@CrossOrigin
@RestController
@RequestMapping("/admin/edu/course")
public class CourseAdminController {

    @Autowired
    private CourseService service;

    @ApiOperation("保存基本课程信息")
    @PostMapping("save-course-info")
    public R saveCourseInfoForm(
            @ApiParam(name = "courseInfoForm", value = "课程基本信息表单对象", required = true)
            @RequestBody CourseInfoForm courseInfoForm) {

        String courseId = service.saveCourseInfoForm(courseInfoForm);
        return R.ok(courseId).setMsg("课程基本信息保存成功");
    }

    @ApiOperation("根据课程id查询课程及描述信息")
    @GetMapping("course-info/{id}")
    public R getCourseInfoFormById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id) {

        CourseInfoForm courseInfoForm = service.getCourseInfoFormById(id);
        return R.ok(courseInfoForm);
    }

    @ApiOperation("更新课程")
    @PutMapping("update-course-info/{id}")
    public R updateCourseInfoById(
            @ApiParam(name = "CourseInfoForm", value = "课程基本信息", required = true)
            @RequestBody CourseInfoForm courseInfoForm,

            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id) {

        service.updateCourseInfoById(id, courseInfoForm);
        return R.ok(null);
    }

    @ApiOperation(value = "分页课程列表")
    @GetMapping("{page}/{limit}")
    public R pageQuery(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam("查询对象")
                    CourseQuery courseQuery) {

        IPage<Course> courseIPage = service.pageQuery(new Page<>(page, limit), courseQuery);

        Map pageMap = new HashMap<String, Object>();
        pageMap.put("total", courseIPage.getTotal());
        pageMap.put("rows", courseIPage.getRecords());
        return R.ok(pageMap);
    }

    @ApiOperation(value = "根据ID删除课程")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id) {

        boolean result = service.removeCourseById(id);
        return R.ok(result);
    }

}

