package com.example.management_platform.controller;


import com.example.management_platform.common.R;
import com.example.management_platform.dto.AdminDto;
import com.example.management_platform.entity.Admin;
import com.example.management_platform.service.impl.AdminService;
import com.example.management_platform.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/admin")
public class AdminController {


    @Autowired
    private AdminService adminService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private JwtUtils jwtUtils;
    /**
     * 进行管理员老师注册的接口  但是此处没有进行验证码的校验
     * @param adminDto
     * @return
     */
    @PostMapping("/register")
    public R<String> register(@RequestBody AdminDto adminDto) {
        log.info("注册");
        //实现验证码的验证的逻辑
        String code=null;
        code=stringRedisTemplate.opsForValue().get(adminDto.getAdminEmail());
        // 检查验证码是否为空
        if (code==null||code.isEmpty()) {
            return R.error("输入信息有误");
        }

        if(!code.equals(adminDto.getVerificationCode())){
            return R.error("验证码错误 请核对后重新输入");
        }

        try {
            // 注册用户
            adminService.register(adminDto);
            return R.success("注册成功");
        } catch (Exception e) {
            log.error("注册失败: {}", e.getMessage(), e);
            return R.error("注册失败，请稍后再试");
        }
    }


    /**
     * 登录功能的实现
     * @param admin
     * @return
     */
    @PostMapping("/login")
    public R<String> login(@RequestBody Admin admin) {

        Admin res=adminService.login(admin);
        log.info("查找结果{}",res);
        if(res==null){
            return R.error("登录失败");
        }
        // 创建JWT 放ID 用户名 以及职位
        Map<String,Object> cliams = new HashMap<>();
        cliams.put("adminId",res.getAdminId());
        cliams.put("adminUsername",res.getAdminUsername());
        cliams.put("adminEmail",res.getAdminEmail());
        cliams.put("role","admin");

        String jwt = jwtUtils.generateJwt(cliams);
        log.info(jwt);
        log.info("登录的管理员信息为：{}",admin);
        //把令牌存到响应体中
        //JwtResponse jwtResponse=new JwtResponse(jwt,admin.getAdminId(), admin.getAdminUsername());
        return R.success(jwt);
    }


    /**
     * 忘记密码时根据邮箱重新设置密码
     * @param adminDto
     * @return
     */
    @PostMapping("/forget-password")
    public R<String> forgetPassword(@RequestBody AdminDto adminDto) {
        //实现验证码的验证的逻辑
        String code=null;
        code=stringRedisTemplate.opsForValue().get(adminDto.getAdminEmail());
        // 检查验证码是否为空
        if (code==null||code.isEmpty()) {
            return R.error("输入信息有误");
        }

        if(!code.equals(adminDto.getVerificationCode())){
            return R.error("验证码错误 请核对重新输入");
        }

        try {
            // 注册用户
            adminService.findBack(adminDto);
            return R.success("找回成功");
        } catch (Exception e) {
            log.error("注册失败: {}", e.getMessage(), e);
            return R.error("注册失败，请稍后再试");
        }





    }




}
