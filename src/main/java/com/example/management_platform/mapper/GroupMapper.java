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
}
