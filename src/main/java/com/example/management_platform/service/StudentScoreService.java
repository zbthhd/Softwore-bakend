package com.example.management_platform.service;

import jakarta.servlet.ServletOutputStream;

public interface StudentScoreService {
    void deleteByClassId(Integer classId);

    void exportExcel(ServletOutputStream outputStream, String 学生成绩表, Integer classId);
}
