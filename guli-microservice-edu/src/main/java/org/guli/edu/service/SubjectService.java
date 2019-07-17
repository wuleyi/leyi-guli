package org.guli.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.guli.edu.entity.Subject;
import org.guli.edu.entity.vo.SubjectNestedVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author Leyi
 * @since 2019-06-04
 */
public interface SubjectService extends IService<Subject> {

    List<String> batchImport(MultipartFile file);

    List<SubjectNestedVo> nestedList();

    Boolean removeById(String id);

    boolean saveLevelOne(Subject subject);

    boolean saveLevelTwo(Subject subject);

}
