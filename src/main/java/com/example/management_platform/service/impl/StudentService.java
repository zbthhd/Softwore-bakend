package com.example.management_platform.service.impl;

import com.example.management_platform.dto.StudentDto;
import com.example.management_platform.entity.Admin;
import com.example.management_platform.entity.Student;
import com.example.management_platform.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
