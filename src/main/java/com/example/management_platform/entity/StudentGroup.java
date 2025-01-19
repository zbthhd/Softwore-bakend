package com.example.management_platform.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentGroup {
    private Integer studentId;
    private String studentName;
    private String studentNumber;
    private Byte studentPosition;
    private Integer groupId;
    private String studentApplyReason;
    private String studentNotice;
    private String giteeUrl;


}
