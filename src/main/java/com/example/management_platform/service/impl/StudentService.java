package com.example.management_platform.service.impl;

import com.example.management_platform.common.R;
import com.example.management_platform.dto.StudentDto;
import com.example.management_platform.entity.Admin;
import com.example.management_platform.entity.Student;
import com.example.management_platform.mapper.StudentMapper;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.stereotype.Service;


@Service
public class StudentService implements com.example.management_platform.service.StudentService {

    @Autowired
    StudentMapper studentMapper;

    @Override
    public R register(StudentDto studentDto) {

        Student student=new Student();
        student.setStudentEmail(studentDto.getStudentEmail());
        student.setStudentPassword(studentDto.getStudentPassword());
        student.setStudentName(studentDto.getStudentUsername());
        student.setStudentId(studentDto.getStudentId());

        //判断该学生是否已经存在
        if(studentMapper.find(student)==null){
            //失败
            return R.success("失败");
        }else{
            //成功--加入到表中
            studentMapper.add(student);
            return R.success("成功");
        }
    }

}
