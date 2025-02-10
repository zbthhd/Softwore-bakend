package com.example.management_platform.mapper;

import com.example.management_platform.dto.StudentDto;
import com.example.management_platform.entity.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper {
    void deleteByClassId(Integer classId);

    Student selectById(Integer studentId);

    Student selectByStudentUsername(String studentUsername);

    void updateByNameAndEmail(Student student);

    Student selectByEmailAndUsername(StudentDto studentDto);

    void updateByStudentUsername(StudentDto studentDto);

    void insert(StudentDto studentDto);

    Student selectByUsernameAndPassword(Student student);
}