package com.example.management_platform.mapper;

import com.example.management_platform.dto.AdminDto;
import com.example.management_platform.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {

    void insert(Admin admin);

    Admin selectByAdminUsernameAndAdminPassword(Admin admin);

    void updateByNameAndEmail(Admin admin);

    Admin selectById(String id);
}
