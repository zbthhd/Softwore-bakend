package com.example.management_platform.controller;


import com.example.management_platform.common.R;
import com.example.management_platform.dto.AdminDto;
import com.example.management_platform.entity.Admin;
import com.example.management_platform.service.impl.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/register")
    public R<String> register(@RequestBody AdminDto adminDto) {
        log.info("注册");
        //实现验证码的验证的逻辑
        if (!adminDto.getVerificationCode().equals("11"))
        {
            log.info("验证码错误");
            return R.error("验证码错误");
        }
        adminService.register(adminDto);

        return R.success("注册成功");
        }


    /**
     * 登录功能的实现
     * @param admin
     * @return
     */
    @PostMapping("/login")
    public R<String> login(@RequestBody Admin admin) {

        Admin res=adminService.login(admin);

        if(res==null){
            return R.error("登录失败");
        }
        // 创建JWT

        //String jwt = jwtUitls.createToken(admin.getAdminId(), admin.getAdminUsername()); // userId, username是从用户信息中提取的
        log.info("登录的管理员信息为：{}",admin);
        //把令牌存到响应体中
        //JwtResponse jwtResponse=new JwtResponse(jwt,admin.getAdminId(), admin.getAdminUsername());
        return R.success("成功");
    }


    /**
     * 忘记密码时根据邮箱重新设置密码
     * @param adminDto
     * @return
     */
    @PostMapping("/forget-password")
    public R<String> forgetPassword(@RequestBody AdminDto adminDto) {
        //实现验证码的验证的逻辑
        if (!adminDto.getVerificationCode().equals("11"))
        {
            log.info("验证码错误");
            return R.error("验证码错误");
        }
        adminService.findBack(adminDto);
        return R.success("找回成功");

    }




}
