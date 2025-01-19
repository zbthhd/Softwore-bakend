package com.example.management_platform.service.impl;


import com.example.management_platform.mapper.StudentGroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentGroupService implements com.example.management_platform.service.StudentGroupService {

    @Autowired
    private StudentGroupMapper studentGroupMapper;


    @Override
    public void deleteByClassId(Integer classId) {
        studentGroupMapper.deleteByClassId(classId);

    }
}
