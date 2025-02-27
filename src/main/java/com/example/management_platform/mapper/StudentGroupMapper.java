package com.example.management_platform.mapper;


import com.example.management_platform.entity.Group;
import com.example.management_platform.entity.StudentGroup;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentGroupMapper {
    void deleteByClassId(Integer classId);

    void updateInfoById(Integer studentId);

    List<StudentGroup> selectByGroupId(Integer groupId);

    void updateInfoByStudentId(Integer studentId);

    void updateApplyInfo(StudentGroup studentGroup);

    StudentGroup selectByStudentId(Integer studentId);

    List<StudentGroup> searchByPageAndId(Integer groupId);

    void updateUrlByGroupId(StudentGroup studentGroup);

    void insertByStudent(StudentGroup studentGroup);

    void updateLeaderInfoByStudentId(StudentGroup studentGroup);

    void updateInfoByStudent(StudentGroup studentGroup);

    void updateAllowByStudentId(Integer studentId);

    void updateRejectByStudentId(Integer studentId);

    void updateScoreByGroupId(Group group);
}
