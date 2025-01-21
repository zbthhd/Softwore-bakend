package com.example.management_platform.service;

import com.example.management_platform.common.R;
import com.example.management_platform.entity.Group;
import com.example.management_platform.entity.PageBeanGroup;
import jakarta.servlet.ServletOutputStream;

import java.util.List;

public interface GroupService {
    PageBeanGroup getGroupByClassId(Integer page,Integer pageSize,Integer classId);

    void deleteByClassId(Integer classId);

    Group getGroupInfoByGroupId(int groupId);

    void evaluationApproval(Group group);

    void exportExcel(ServletOutputStream outputStream, String title,Integer classId);

    void deleteByStudentId(Integer studentId);

    Group searchByStudentId(Integer studentId);

    void enterNext(Integer studentId);

    void addGiteeUrl(Group group);

    Group getGroupInfoByStudentId(Integer studentId);
}
