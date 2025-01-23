package com.example.management_platform.service;

import com.example.management_platform.dto.StudentDto;
import com.example.management_platform.entity.Student;

public interface StudentService {
    void deleteByClassId(Integer classId);

    Student selectUserById(Integer studentId);

    Student searchByStudentUsername(String studentUsername);


    void findBack(StudentDto studentDto);

    Student searchByEmailAndUsername(StudentDto studentDto);

    void updateInfo(StudentDto studentDto);

    void create(StudentDto studentDto);

    Student searchByUsernameAndPassword(Student student);

    Student searchByStudentId(Integer studentId);
}
