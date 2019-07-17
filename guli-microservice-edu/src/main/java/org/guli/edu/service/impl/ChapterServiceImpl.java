package org.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.guli.common.util.Assert;
import org.guli.common.util.GuliUtils;
import org.guli.edu.entity.Chapter;
import org.guli.edu.entity.Video;
import org.guli.edu.mapper.ChapterMapper;
import org.guli.edu.service.ChapterService;
import org.guli.edu.service.VideoService;
import org.guli.edu.entity.vo.ChapterVo;
import org.guli.edu.entity.vo.VideoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Leyi
 * @since 2019-06-04
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoService videoService;

    @Override
    public List<ChapterVo> nestedList(String courseId) {

        List<ChapterVo> chapterVoList = new ArrayList<>();

        List<Video> videoList = videoService.list(new QueryWrapper<Video>().eq("course_id", courseId).orderByAsc("sort", "id"));

        this.list(new QueryWrapper<Chapter>().eq("course_id", courseId).orderByAsc("sort", "id")).forEach(chapter -> {

            ChapterVo chapterVo = GuliUtils.copyPropertiesPlus(chapter, new ChapterVo());
            chapterVoList.add(chapterVo);

            List<VideoVo> subVideoList = new ArrayList<>();
            videoList.forEach(video -> {

                if (video.getChapterId().equals(chapter.getId()))
                    subVideoList.add(GuliUtils.copyPropertiesPlus(video, new VideoVo()));
            });

            chapterVo.setChildren(subVideoList);
        });
        return chapterVoList;
    }

    @Override
    public boolean removeChapterById(String id) {

        // 先判断当前章节下是否存在视频，如果存在，给出提示再选择是否进行连带删除
        Assert.isTrue(videoService.count(new LambdaQueryWrapper<Video>().eq(Video::getChapterId, id)) == 0, "该分章节下存在视频课程，请先删除视频课程");

        return this.removeById(id);
    }

    @Override
    public void saveChapter(Chapter chapter) {

        Assert.isTrue(this.save(chapter), "保存章节失败");
    }

}
