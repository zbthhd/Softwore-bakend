package com.example.management_platform.mapper;

import com.example.management_platform.entity.ClassInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClassMapper {
    void insert(ClassInfo classInfo);

    List<ClassInfo> getClasses();

    List<ClassInfo> searchByPageAndName(String name, Integer adminId);

    void deleteById(Integer classId);

    ClassInfo selectById(Integer classId);

    void updateStudentNumber(ClassInfo classInfo);
}
