package org.guli.edu.controller.admin;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.guli.common.exception.GuliException;
import org.guli.edu.entity.Teacher;
import org.guli.edu.query.TeacherQuery;
import org.guli.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by leyi on 2019/6/4.
 */
@Api("讲师管理")
@RestController
@RequestMapping("/admin/edu/teacher")
public class TeacherAdminController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation(value = "获取讲师列表")
    @GetMapping
    public R listTeachers() {

        return R.ok(teacherService.list(null));
    }

    @ApiOperation(value = "根据ID删除讲师（逻辑删除）")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {

        return R.ok(teacherService.removeById(id));
    }

    @ApiOperation(value = "分页讲师列表")
    @GetMapping("{current}/{size}")
    public R pageList(
            @ApiParam(name = "current", value = "当前页码", required = true)
            @PathVariable Integer current,
            @ApiParam(name = "size", value = "每页记录数", required = true)
            @PathVariable Integer size,
            @ApiParam(name = "teacherQuery", value = "讲师条件查询")
                    TeacherQuery teacherQuery) {

        if (current <= 0 || size <= 0) {
            throw new GuliException("当前页码或每页记录数不能小于0！");
        }

        Page<Teacher> page = new Page<>(current, size);
        return R.ok(teacherService.pageQuery(page, teacherQuery));
    }

    @ApiOperation(value = "新增讲师")
    @PostMapping
    public R save(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody Teacher teacher) {

        return R.ok(teacherService.save(teacher));
    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("{id}")
    public R getById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {

        return R.ok(teacherService.getById(id));
    }

    @ApiOperation(value = "根据ID修改讲师")
    @PutMapping("{id}")
    public R updateById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id,
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody Teacher teacher) {

        teacher.setId(id);
        return R.ok(teacherService.updateById(teacher));
    }

}
