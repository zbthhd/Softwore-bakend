package com.example.management_platform.service.impl;

import com.example.management_platform.dto.StudentDto;
import com.example.management_platform.entity.Admin;
import com.example.management_platform.entity.Student;
import com.example.management_platform.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.management_platform.common.R;
import com.example.management_platform.dto.StudentDto;
import com.example.management_platform.entity.Admin;
import com.example.management_platform.entity.Student;
import com.example.management_platform.mapper.StudentMapper;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


@Service
public class StudentService implements com.example.management_platform.service.StudentService {
    @Autowired
    private StudentMapper StudentMapper;
    @Autowired
    private StudentMapper studentMapper;


    @Override
    public void deleteByClassId(Integer classId) {
        studentMapper.deleteByClassId(classId);
    }

    @Override
    public Student selectUserById(Integer studentId) {

        return studentMapper.selectById(studentId);

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

    @Override
    public Student searchByStudentUsername(String studentUsername) {
        return studentMapper.selectByStudentUsername(studentUsername);
    }

    @Override
    public void findBack(StudentDto studentDto) {
        //将adminDto的信息传给admin对象
        Student student = new Student();
        student.setStudentUsername(studentDto.getStudentUsername());
        student.setStudentPassword(studentDto.getStudentPassword());
        student.setStudentEmail(studentDto.getStudentEmail());

        //对密码进行MD5加密
        String password=student.getStudentPassword();
        student.setStudentPassword(DigestUtils.md5DigestAsHex(password.getBytes()));;

        studentMapper.updateByNameAndEmail(student);

    }

    @Override
    public Student searchByEmailAndUsername(StudentDto studentDto) {
        return studentMapper.selectByEmailAndUsername(studentDto);
    }

    @Override
    public void updateInfo(StudentDto studentDto) {
        String studentPassword = studentDto.getStudentPassword();
        studentDto.setStudentPassword(DigestUtils.md5DigestAsHex(studentPassword.getBytes()));
        studentMapper.updateByStudentUsername(studentDto);
    }

    @Override
    public void create(StudentDto studentDto) {

        String studentPassword = studentDto.getStudentPassword();
        studentDto.setStudentPassword(DigestUtils.md5DigestAsHex(studentPassword.getBytes()));

        studentMapper.insert(studentDto);
    }

    @Override
    public Student searchByUsernameAndPassword(Student student) {

        String studentPassword = student.getStudentPassword();
        student.setStudentPassword(DigestUtils.md5DigestAsHex(studentPassword.getBytes()));
        return studentMapper.selectByUsernameAndPassword(student);

    }


}
