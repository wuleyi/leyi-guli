package org.guli.edu.controller.admin;

import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.guli.edu.entity.Subject;
import org.guli.edu.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api("课程分类管理")
@CrossOrigin
@RestController
@RequestMapping("/admin/edu/subject")
public class SubjectAdminController {

    @Autowired
    private SubjectService service;

    @ApiOperation(value = "Excel批量导入")
    @PostMapping("import")
    public R addUser(
            @ApiParam(name = "file", value = "Excel文件", required = true)
            @RequestParam("file") MultipartFile file) {

        List<String> msg = service.batchImport(file);
        return msg.size() == 0 ? R.ok("数据导入成功") : R.failed("部分数据导入失败").setData(msg);
    }

    @ApiOperation(value = "嵌套数据列表")
    @GetMapping
    public R nestedList() {

        return R.ok(service.nestedList());
    }

    @ApiOperation(value = "根据ID删除课程分类（逻辑删除）")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id) {

        return R.ok(service.removeById(id));
    }

    @ApiOperation("新增一级分类")
    @PostMapping("save-level-one")
    public R saveLevelOne(
            @ApiParam(name = "subject", value = "课程分类对象", required = true)
            @RequestBody Subject subject) {

        if (service.saveLevelOne(subject)) return R.ok(null);
        else return R.failed("新增一级分类失败");
    }

    @ApiOperation(value = "新增二级分类")
    @PostMapping("save-level-two")
    public R saveLevelTwo(
            @ApiParam(name = "subject", value = "课程分类对象", required = true)
            @RequestBody Subject subject) {

        if (service.saveLevelTwo(subject)) return R.ok(null);
        else return R.failed("新增二级分类失败");
    }

}