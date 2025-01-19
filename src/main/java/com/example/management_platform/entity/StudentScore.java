package com.example.management_platform.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentScore {

    private String studentName;
    private Integer studentId;
    private String studentNumber;
    private String groupProName;
    private Byte groupScore;
    private Integer groupId;
    private Byte studentFinish;
}
