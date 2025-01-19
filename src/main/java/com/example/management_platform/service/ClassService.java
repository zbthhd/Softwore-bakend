package com.example.management_platform.service;

import com.example.management_platform.entity.ClassInfo;
import com.example.management_platform.entity.PageBeanClasses;

import java.util.List;

public interface ClassService {
    void create(ClassInfo classInfo);

    List<ClassInfo> getClasses();

    PageBeanClasses page(Integer page, Integer pageSize, String name);

    void deleteClass(Integer classId);
}
