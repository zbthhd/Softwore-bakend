package com.example.management_platform.service.impl;

import com.example.management_platform.dto.AdminDto;
import com.example.management_platform.entity.Admin;
import com.example.management_platform.mapper.AdminMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

@Service
public class AdminService implements com.example.management_platform.service.AdminService {

    private static final Logger log = LoggerFactory.getLogger(AdminService.class);
    @Autowired
    private AdminMapper adminMapper;


    @Transactional
    @Override
    public void register(AdminDto adminDto) {
        //将adminDto的信息传给admin对象
        Admin admin = new Admin();
        admin.setAdminUsername(adminDto.getAdminUsername());
        admin.setAdminPassword(adminDto.getAdminPassword());
        admin.setAdminEmail(adminDto.getAdminEmail());


        //对密码进行MD5加密
        String password=admin.getAdminPassword();
        admin.setAdminPassword(DigestUtils.md5DigestAsHex(password.getBytes()));;

        //对新创建的对象进行加入
        adminMapper.insert(admin);
        return;
    }
}
