package com.example.management_platform.dto;

import com.example.management_platform.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto extends Student {
    private String verificationCode;
    private Integer classId;
    private String studentPasswordPre;
}
