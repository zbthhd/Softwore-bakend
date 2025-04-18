package com.example.management_platform.service.impl;


import com.example.management_platform.dto.StudentGroupDto;
import com.example.management_platform.entity.*;
import com.example.management_platform.mapper.StudentGroupMapper;
import com.example.management_platform.mapper.StudentMapper;
import com.example.management_platform.mapper.StudentScoreMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentGroupService implements com.example.management_platform.service.StudentGroupService {

    @Autowired
    private StudentGroupMapper studentGroupMapper;
    @Autowired
    private StudentScoreMapper studentScoreMapper;
    @Autowired
    private StudentMapper studentMapper;


    @Transactional
    @Override
    public void deleteByClassId(Integer classId) {
        studentGroupMapper.deleteByClassId(classId);

    }

    @Transactional
    @Override
    public void expelFromGroup(Integer studentId) {
        studentGroupMapper.updateInfoById(studentId);
    }

    @Transactional
    @Override
    public List<StudentGroup> searchAllByGroupId(Integer groupId) {
        return studentGroupMapper.selectByGroupId(groupId);
    }

    @Transactional
    @Override
    public void updateByList(List<StudentGroup> list) {
        //依次取出来这表中的信息
        for (StudentGroup studentGroup : list) {
            Integer studentId = studentGroup.getStudentId();
            studentGroupMapper.updateInfoByStudentId(studentId);
            studentScoreMapper.updateInfoByStudentId(studentId);
        }

    }

    @Transactional
    @Override
    public void applyGroupByGroupId(StudentGroup studentGroup) {
        studentGroupMapper.updateApplyInfo(studentGroup);
    }

    @Transactional
    @Override
    public StudentGroupDto searchByStudentId(Integer studentId) {
        StudentGroup studentGroup = studentGroupMapper.selectByStudentId(studentId);
        StudentGroupDto studentGroupDto=new StudentGroupDto();
        BeanUtils.copyProperties(studentGroup, studentGroupDto);
        //赋值另外的用户名和邮箱
        Student student = studentMapper.selectById(studentGroup.getStudentId());
        studentGroupDto.setStudentEmail(student.getStudentEmail());
        studentGroupDto.setStudentUsername(student.getStudentUsername());
        return studentGroupDto;
    }

    @Transactional
    @Override
    public PageBeanStudentGroup page(Integer page, Integer pageSize,Integer groupId) {
        //设置分页参数
        PageHelper.startPage(page,pageSize);
        //执行查询 根据姓名进行查询 将这个姓名相关的人全部查询出来
        List<StudentGroup> list=studentGroupMapper.searchByPageAndId(groupId);

        // 创建 PageInfo 对象 用于分页
        PageInfo<StudentGroup> pageInfo = new PageInfo<>(list);

        return new PageBeanStudentGroup(pageInfo.getList(),(long)pageInfo.getTotal());

    }

    @Transactional
    @Override
    public void applyGroupByStudentId(StudentGroup studentGroup) {
        studentGroupMapper.updateInfoByStudent(studentGroup);
    }

    @Transactional
    @Override
    public void updateByStudentId(Integer studentId) {
        studentGroupMapper.updateAllowByStudentId(studentId);
    }

    @Transactional
    @Override
    public void updateRejectStudentId(Integer studentId) {
        studentGroupMapper.updateRejectByStudentId(studentId);
        //学生成绩表 groupId  groupName
        studentScoreMapper.updateRejectByStudentId(studentId);
    }


}
