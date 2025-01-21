package com.example.management_platform.mapper;


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
}
