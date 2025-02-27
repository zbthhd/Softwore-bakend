package com.example.management_platform.controller;


import com.example.management_platform.common.Mail;
import com.example.management_platform.common.R;
import com.example.management_platform.service.impl.GroupService;
import com.example.management_platform.service.impl.StudentScoreService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tools.ant.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("api/common")
@Slf4j
public class CommonController {
    @Autowired
    private Mail mail;
    @Autowired
    private GroupService groupService;
    @Autowired
    private StudentScoreService studentScoreService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;



    @GetMapping("/get-verification/{email}")
    public R<Object> getVerification(@PathVariable String email) {
        log.info("Get verification email: {}", email);
        try {
            int code = (int)((Math.random()*9+1)*1000);
            mail.sendSimpleMail(code+"",email);
            stringRedisTemplate.opsForValue().set(email,String.valueOf(code), 5, TimeUnit.MINUTES);
            return R.success(code);
        } catch (Exception e) {
            return R.error("获取验证码失败");
        }
    }

    @GetMapping("/export-groups/{classId}")
    public void exportGroups(@PathVariable Integer classId, HttpServletResponse response) throws IOException {

        log.info("下载小组信息为excel表...");
        // 创建导出文件名称 当前日期+前台传递过来的标题名（excelTitle）
        String fileName = DateUtils.format(new Date(),"yyyyMMddHHmmss") +"-"+"小组成绩表"+".xls";
        // 设置返回的消息头和返回值类型 并设置编码 不设置编码文件名为中文的话 不会显示
        // 当设置成如下返回值时，浏览器才会执行下载文件动作
        try {
            // 设置 Content-Disposition 头部，并确保文件名编码正确
            String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\"");

            // 设置正确的 MIME 类型
            response.setContentType("application/vnd.ms-excel");

            // 创建输出流，调用service中exportExcel方法
            groupService.exportExcel(response.getOutputStream(), "小组成绩表", classId);
            response.getOutputStream().flush();
        } catch (Exception e) {
            log.error("导出 Excel 文件失败: ", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("导出失败，请联系管理员！");
        }

    }
    @GetMapping("/export-students/{classId}")
    public void exportStudents(@PathVariable Integer classId, HttpServletResponse response) throws IOException {

        log.info("下载学生信息为excel表...");
        // 创建导出文件名称 当前日期+前台传递过来的标题名（excelTitle）
        String fileName = DateUtils.format(new Date(),"yyyyMMddHHmmss") +"-"+"学生成绩表"+".xls";
        // 设置返回的消息头和返回值类型 并设置编码 不设置编码文件名为中文的话 不会显示
        // 当设置成如下返回值时，浏览器才会执行下载文件动作
        response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("APPLICATION/OCTET-STREAM;charset=UTF-8");
        // 创建输出流，调用service中exportTest方法，参数：输出流 标题名
        studentScoreService.exportExcel(response.getOutputStream(), "学生成绩表",classId);

    }


}
