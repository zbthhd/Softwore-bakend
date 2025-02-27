package com.example.management_platform.dto;

import com.example.management_platform.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentUploadDto extends Student {
    private MultipartFile file;

}
