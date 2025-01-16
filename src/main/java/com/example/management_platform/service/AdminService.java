package com.example.management_platform.service;

import com.example.management_platform.dto.AdminDto;
import com.example.management_platform.entity.Admin;

public interface AdminService {
    void register(AdminDto adminDto);
}
