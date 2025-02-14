package com.example.management_platform.dto;

import com.example.management_platform.entity.StudentGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentGroupDto extends StudentGroup {
    private String studentUsername;
    private String studentEmail;
}
