package com.example.management_platform.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    private String studentUsername;
    private String studentName;
    private Integer studentId;
    private String studentNumber;
    private String studentPassword;

    private String studentEmail;
    private Integer classId;





}
