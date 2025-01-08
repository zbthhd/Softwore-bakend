package com.example.management_platform.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Class {
    private Integer classId;
    private String adminUsername;
    private String className;
    private Byte classStudentNumber;
    private Integer adminId;
}
