package com.example.management_platform.dto;

import com.example.management_platform.entity.Admin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto extends Admin {
    private String verificationCode;
}
