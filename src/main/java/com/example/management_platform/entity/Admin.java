package com.example.management_platform.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    private Integer adminId;
    private String adminName;
    private String adminPassword;
    private String adminEmail;
}
