package com.example.management_platform.mapper;

import com.example.management_platform.entity.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper {
    void deleteByClassId(Integer classId);

    Student selectById(String studentId);
}
