package com.example.management_platform.service.impl;

import com.example.management_platform.entity.Student;
import com.example.management_platform.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StudentService implements com.example.management_platform.service.StudentService {
    @Autowired
    private StudentMapper StudentMapper;
    @Autowired
    private StudentMapper studentMapper;


    @Override
    public void deleteByClassId(Integer classId) {
        studentMapper.deleteByClassId(classId);
    }

    @Override
    public Student selectUserById(String studentId) {
        return studentMapper.selectById(studentId);
    }

}
