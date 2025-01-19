package com.example.management_platform.service;

import com.example.management_platform.entity.Student;

public interface StudentService {
    void deleteByClassId(Integer classId);

    Student selectUserById(String studentId);
}
