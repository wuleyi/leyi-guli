package org.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.guli.edu.entity.Teacher;
import org.guli.edu.mapper.TeacherMapper;
import org.guli.edu.query.TeacherQuery;
import org.guli.edu.service.TeacherService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author Leyi
 * @since 2019-06-04
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Override
    public Map<String, Object> pageQuery(IPage<Teacher> iPage, TeacherQuery teacherQuery) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        // queryWrapper.orderByDesc("sort"); //根据数据库sort列排序

        if (teacherQuery != null) {

            String name = teacherQuery.getName();
            if (!StringUtils.isEmpty(name)) {
                queryWrapper.likeRight("name", name);
            }

            Integer level = teacherQuery.getLevel();
            if (!StringUtils.isEmpty(level)) {
                queryWrapper.eq("level", level);
            }

            String begin = teacherQuery.getBegin();
            if (!StringUtils.isEmpty(begin)) {
                queryWrapper.ge("gmt_create", begin);
            }

            String end = teacherQuery.getEnd();
            if (!StringUtils.isEmpty(end)) {
                queryWrapper.le("gmt_modified", end);
            }
        }

        baseMapper.selectPage(iPage, queryWrapper);

        HashMap<String, Object> pageMap = new HashMap<>();
        pageMap.put("rows", iPage.getRecords());
        pageMap.put("total", iPage.getTotal());

        return pageMap;
    }

}
