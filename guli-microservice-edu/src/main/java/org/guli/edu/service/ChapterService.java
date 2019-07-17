package org.guli.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.guli.edu.entity.Chapter;
import org.guli.edu.entity.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Leyi
 * @since 2019-06-04
 */
public interface ChapterService extends IService<Chapter> {

    List<ChapterVo> nestedList(String courseId);

    boolean removeChapterById(String id);

    void saveChapter(Chapter chapter);

}
