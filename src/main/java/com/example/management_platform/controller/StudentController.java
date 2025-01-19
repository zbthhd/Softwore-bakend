package com.example.management_platform.controller;

import com.example.management_platform.common.R;
import com.example.management_platform.dto.AdminDto;
import com.example.management_platform.dto.StudentDto;
import com.example.management_platform.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    /**
     * 学生注册
     * @param
     * @return
     */
    @PostMapping("/register")
    public R<String> StudentRegister(@RequestBody StudentDto studentDto){
        return studentService.register(studentDto);
    }
}
