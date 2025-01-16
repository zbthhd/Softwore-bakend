package com.example.management_platform.controller;


import com.example.management_platform.common.R;
import com.example.management_platform.dto.AdminDto;
import com.example.management_platform.entity.Admin;
import com.example.management_platform.service.impl.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 进行管理员老师注册的接口  但是此处没有进行验证码的校验
     * @param adminDto
     * @return
     */
    @PostMapping("register")
    public R<String> register(@RequestBody AdminDto adminDto) {

        //实现验证码的验证的逻辑
        if (!adminDto.getVerificationCode().equals("11"))
        {
            log.info("验证码错误");
            return R.error("验证码错误");
        }
        adminService.register(adminDto);

        return R.success("注册成功");
        }

}
