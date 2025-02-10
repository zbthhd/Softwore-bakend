package com.example.management_platform.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Group {
    private Integer groupId;
    private Integer studentId;
    private String groupName;
    private String groupProName;
    private Byte groupScore;
    private Byte groupIsAvailable;
    private String groupLeader;
    private String groupNotice;
    private Byte groupFinish;
    private Integer classId;
    private String giteeUrl;
}

