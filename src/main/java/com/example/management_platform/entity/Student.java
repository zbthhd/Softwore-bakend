package com.example.management_platform.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private Integer studentId;
    private String studentUsername;
    private String studentName;
    private String studentPassword;
    private String studentEmail;
    private Byte studentPosition;
    private Byte studentFinish;
    private String studentTeamLeaderNotice;
    private String studentTeamTeacherNotice;
    private String studentApplyReason;
    private Byte studentStatus;
    private Byte studentScore;
    private Integer groupId;
    private Integer classId;
    private String giteeUrl;
}
