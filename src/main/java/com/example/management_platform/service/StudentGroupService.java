package com.example.management_platform.service;

import com.example.management_platform.entity.PageBeanStudentGroup;
import com.example.management_platform.entity.StudentGroup;

import java.util.List;

public interface StudentGroupService {
    void deleteByClassId(Integer classId);


    void expelFromGroup(Integer studentId);

    List<StudentGroup> searchAllByGroupId(Integer groupId);

    void updateByList(List<StudentGroup> list);

    void applyGroupByGroupId(StudentGroup studentGroup);

    StudentGroup searchByStudentId(Integer studentId);

    PageBeanStudentGroup page(Integer page, Integer pageSize,Integer groupId);

}
