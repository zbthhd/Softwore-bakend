package com.example.management_platform.service;

import com.example.management_platform.entity.PageBeanStudentScore;
import com.example.management_platform.entity.Student;
import com.example.management_platform.entity.StudentGroup;
import com.example.management_platform.entity.StudentScore;
import jakarta.servlet.ServletOutputStream;

public interface StudentScoreService {
    void deleteByClassId(Integer classId);

    void exportExcel(ServletOutputStream outputStream, String 学生成绩表, Integer classId);

    void expelFromGroup(Integer studentId);

    void applyGroupByGroupId(StudentGroup studentGroup);

    void enterNext(Integer studentId);

    PageBeanStudentScore page(Integer page, Integer pageSize, String classId);

    StudentScore searchByStudentId(Integer studentId);

    PageBeanStudentScore pageGroup(Integer page, Integer pageSize, String groupId);
}
