package org.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.guli.common.exception.GuliException;
import org.guli.common.util.ExcelImportUtils;
import org.guli.edu.entity.Course;
import org.guli.edu.entity.Subject;
import org.guli.edu.mapper.CourseMapper;
import org.guli.edu.mapper.SubjectMapper;
import org.guli.edu.service.SubjectService;
import org.guli.edu.entity.vo.SubjectNestedVo;
import org.guli.edu.entity.vo.SubjectVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author Leyi
 * @since 2019-06-04
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Resource
    private CourseMapper courseMapper;


    /**
     * 根据分类名称查询这个一级分类是否存在
     *
     * @param title
     * @return
     */
    private Subject getByTitle(String title) {

        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", title);
        queryWrapper.eq("parent_id", "0");
        return baseMapper.selectOne(queryWrapper);
    }

    /**
     * 根据分类名称和父id查询这个二级分类是否存在
     *
     * @param title
     * @return
     */
    private Subject getSubByTitle(String title, String parentId) {

        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", title);
        queryWrapper.eq("parent_id", parentId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Transactional
    @Override
    public List<String> batchImport(MultipartFile file) {

        List<String> msg = new ArrayList<>();
        try {
            ExcelImportUtils excelHSSFUtil = new ExcelImportUtils(file.getInputStream());
            Sheet sheet = excelHSSFUtil.getSheet();

            int rowCount = sheet.getPhysicalNumberOfRows();
            if (rowCount <= 1) {
                msg.add("请填写数据");
                return msg;
            }
            for (int rowNum = 1; rowNum < rowCount; rowNum++) {

                Row rowData = sheet.getRow(rowNum);
                if (rowData != null) {// 行不为空

                    //一级分类名称
                    String levelOneValue = "";
                    Cell levelOneCell = rowData.getCell(0);
                    if (levelOneCell != null) {
                        levelOneValue = excelHSSFUtil.getCellValue(levelOneCell);
                        if (StringUtils.isEmpty(levelOneValue)) {
                            msg.add("第" + rowNum + "行一级分类为空");
                            continue;
                        }
                    }

                    Subject subject = this.getByTitle(levelOneValue);
                    Subject subjectLevelOne = null;
                    String parentId = null;
                    if (subject == null) {//创建一级分类
                        subjectLevelOne = new Subject();
                        subjectLevelOne.setTitle(levelOneValue);
                        subjectLevelOne.setSort(0);
                        baseMapper.insert(subjectLevelOne);//添加
                        parentId = subjectLevelOne.getId();
                    } else {
                        parentId = subject.getId();
                    }

                    //二级分类名称
                    String levelTwoValue = "";
                    Cell levelTwoCell = rowData.getCell(1);
                    if (levelTwoCell != null) {
                        levelTwoValue = excelHSSFUtil.getCellValue(levelTwoCell);
                        if (StringUtils.isEmpty(levelTwoValue)) {
                            msg.add("第" + rowNum + "行二级分类为空");
                            continue;
                        }
                    }

                    Subject subjectSub = this.getSubByTitle(levelTwoValue, parentId);
                    Subject subjectLevelTwo = null;
                    if (subjectSub == null) {//创建二级分类
                        subjectLevelTwo = new Subject();
                        subjectLevelTwo.setTitle(levelTwoValue);
                        subjectLevelTwo.setParentId(parentId);
                        subjectLevelTwo.setSort(0);
                        baseMapper.insert(subjectLevelTwo);//添加
                    }
                }
            }

        } catch (Exception e) {
            throw new GuliException("Excel数据导入异常");
        }

        return msg;
    }

    @Override
    public List<SubjectNestedVo> nestedList() {

        //最终要的到的数据列表
        List<SubjectNestedVo> subjectNestedVoList = new ArrayList<>();

        //获取一级分类数据记录
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", 0);
        queryWrapper.orderByAsc("sort", "id");
        List<Subject> subjects = baseMapper.selectList(queryWrapper);

        //获取二级分类数据记录
        QueryWrapper<Subject> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.ne("parent_id", 0);
        queryWrapper2.orderByAsc("sort", "id");
        List<Subject> subSubjects = baseMapper.selectList(queryWrapper2);

        //填充一级分类vo数据
        subjects.forEach(subject -> {
            //创建一级类别vo对象
            SubjectNestedVo subjectNestedVo = new SubjectNestedVo();
            BeanUtils.copyProperties(subject, subjectNestedVo);
            subjectNestedVoList.add(subjectNestedVo);

            //填充二级分类vo数据
            List<SubjectVo> subjectVoList = new ArrayList<>();
            subSubjects.forEach(subSubject -> {
                if (subject.getId().equals(subSubject.getParentId())) {

                    //创建二级类别vo对象
                    SubjectVo subjectVo = new SubjectVo();
                    BeanUtils.copyProperties(subSubject, subjectVo);
                    subjectVoList.add(subjectVo);
                }
            });
            subjectNestedVo.setChildren(subjectVoList);
        });

        return subjectNestedVoList;
    }

    @Override
    public Boolean removeById(String id) {

        // 根据当前id判断是否有子类别，如果有则提示用户
        if (baseMapper.selectCount(new QueryWrapper<Subject>().eq("parent_id", id)) > 0)
            throw new GuliException("该分类下已经存在子分类，请先删除子分类");

        // 根据当前id判断是否有课程信息，如果有则提示用户
        if (courseMapper.selectCount(new QueryWrapper<Course>().eq("subject_id", id)) > 0)
            throw new GuliException("该分类下已经存在课程信息，请先关联的课程信息");

        return baseMapper.deleteById(id) >= 0;
    }

    @Override
    public boolean saveLevelOne(Subject subject) {

        return null == getByTitle(subject.getTitle()) ? baseMapper.insert(subject) > 0 : false;
    }

    @Override
    public boolean saveLevelTwo(Subject subject) {

        return null == getSubByTitle(subject.getTitle(), subject.getParentId()) ? baseMapper.insert(subject) > 0 : false;
    }

}
