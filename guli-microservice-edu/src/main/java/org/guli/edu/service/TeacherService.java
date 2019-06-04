package org.guli.edu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.guli.edu.entity.Teacher;
import org.guli.edu.query.TeacherQuery;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author Leyi
 * @since 2019-06-04
 */
public interface TeacherService extends IService<Teacher> {

    /**
     * 按条件查询讲师信息
     *
     * @param iPage
     * @param teacherQuery
     */
    Map<String, Object> pageQuery(IPage<Teacher> iPage, TeacherQuery teacherQuery);

}
