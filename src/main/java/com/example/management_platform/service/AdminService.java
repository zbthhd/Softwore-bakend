package com.example.management_platform.service;

import com.example.management_platform.dto.AdminDto;
import com.example.management_platform.entity.Admin;

public interface AdminService {
    void register(AdminDto adminDto);

    Admin login(Admin admin);

    void findBack(AdminDto adminDto);

    Admin selectUserById(String id);

    Admin searchByAdminUsername(String adminUsername);
    void updateInfo(AdminDto adminDto);
}
