package com.example.management_platform.service;

import com.example.management_platform.common.R;
import com.example.management_platform.entity.Group;
import jakarta.servlet.ServletOutputStream;

import java.util.List;

public interface GroupService {
    List<Group> getGroupByClassId(Integer classId);

    void deleteByClassId(Integer classId);

    Group getGroupInfoByGroupId(int groupId);

    void evaluationApproval(Group group);

    void exportExcel(ServletOutputStream outputStream, String title,Integer classId);
}
