package com.example.management_platform.mapper;

import com.example.management_platform.common.R;
import com.example.management_platform.entity.Group;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GroupMapper {
    List<Group> selectByClassId(Integer classId);

    void deleteByClassId(Integer classId);

    Group selectByGroupId(int groupId);

    void updateByGroupId(Group group);

    void deleteByStudentId(Integer studentId);

    Group selectByStudentId(Integer studentId);

    void updateFinishByGroup(Group group);

    void updateUrlByGroupId(Group group);

    List<Group> searchByPageAndClassId(Integer classId);

    void insertByGroupName(Group group);

    void updatePro(Group group);
}
