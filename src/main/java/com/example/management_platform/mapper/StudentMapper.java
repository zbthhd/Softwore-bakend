package com.example.management_platform.mapper;

import com.example.management_platform.entity.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper {

    public Student find(Student student);

    public void add(Student student);
}
