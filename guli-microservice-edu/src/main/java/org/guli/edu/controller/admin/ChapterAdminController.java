package org.guli.edu.controller.admin;


import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.guli.edu.entity.Chapter;
import org.guli.edu.service.ChapterService;
import org.guli.edu.entity.vo.ChapterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Leyi
 * @since 2019-06-04
 */
@CrossOrigin
@RestController
@RequestMapping("/admin/edu/chapter")
public class ChapterAdminController {

    @Autowired
    private ChapterService service;

    @GetMapping("nested-list/{courseId}")
    @ApiOperation("嵌套章节数据列表")
    public R nestedListByCourseId(
            @ApiParam(value = "课程id", required = true)
            @PathVariable String courseId) {

        List<ChapterVo> chapterVoList = service.nestedList(courseId);
        return R.ok(chapterVoList);
    }

    @PostMapping
    @ApiOperation("添加章节")
    public R save(
            @ApiParam(value = "章节对象", required = true)
            @RequestBody Chapter chapter) {

        service.saveChapter(chapter);
        return R.ok("添加章节成功");
    }

    @GetMapping("{id}")
    @ApiOperation("根据id查找章节")
    public R getById(
            @ApiParam(value = "章节id", required = true)
            @PathVariable String id) {

        return R.ok(service.getById(id));
    }

    @PutMapping("{id}")
    @ApiOperation("修改章节")
    public R edit(@ApiParam(name = "id", value = "章节ID", required = true)
                  @PathVariable String id,
                  @ApiParam(name = "chapter", value = "章节对象", required = true)
                  @RequestBody Chapter chapter) {

        chapter.setId(id);
        return R.ok(service.updateById(chapter));
    }

    @ApiOperation(value = "根据ID删除章节")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "章节ID", required = true)
            @PathVariable String id){

        boolean flag = service.removeChapterById(id);
        return R.ok(flag);
    }

}

