package com.example.management_platform.mapper;


import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentGroupMapper {
    void deleteByClassId(Integer classId);
}
