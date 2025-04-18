package com.example.management_platform.service.impl;

import com.example.management_platform.entity.*;
import com.example.management_platform.mapper.GroupMapper;
import com.example.management_platform.mapper.StudentGroupMapper;
import com.example.management_platform.mapper.StudentScoreMapper;
import com.example.management_platform.utils.ExportExcel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.ServletOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

@Service
public class StudentScoreService implements com.example.management_platform.service.StudentScoreService {

    private static final Logger log = LoggerFactory.getLogger(StudentScoreService.class);
    @Autowired
    private StudentScoreMapper studentScoreMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Transactional
    @Override
    public void deleteByClassId(Integer classId) {
        studentScoreMapper.deleteByClassId(classId);

    }

    @Transactional
    @Override
    public void exportExcel(ServletOutputStream outputStream, String tile, Integer classId) {
        // 定义列标 就是一个Excel的每一个字段标题
        String[] rowsName = new String[]{ "学号", "学生姓名","项目名", "得分"};
        // 创建导出数据集合 后续会将dataList中的数据写到Excel
        List<Object[]> dataList = new ArrayList<>();
        // 从数据库查询用户列表（需要的可以自行添加参数）

        List<StudentScore> studentScoreList = studentScoreMapper.selectByClassId(classId);
        StudentScore studentScore = null;
        // 将列表信息封装到一个Object数组
        // 我这里封装Object数组 是为了方便后续代码复用,不会将对象类型写死
        for (int i=0; i<studentScoreList.size(); i++){
            //将数据库查到的每条数据 循环封装到Object[]
            studentScore=studentScoreList.get(i);

            Object[] objs = new Object[]{studentScore.getStudentNumber(),studentScore.getStudentName(),studentScore.getGroupProName(),studentScore.getGroupScore()};
            //将转换好的数据 存入dataList
            dataList.add(objs);
        }
        // 创建ExportExcel工具类对象 通过构造方法赋值
        ExportExcel ex = new ExportExcel(tile, rowsName, dataList);
        try {
            // 调用生成Excel的方法,将数据通过输出流写出
            ex.export(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.flush();
        out.close();
    }

    @Transactional
    @Override
    public void expelFromGroup(Integer studentId) {
        studentScoreMapper.updateExpelById(studentId);
    }

    @Transactional
    @Override
    public void applyGroupByGroupId(StudentGroup studentGroup) {
        //先根据小组的ID 找到这个小组的项目名
        Group group=groupMapper.selectByGroupId(studentGroup.getGroupId());
        //然后将项目名 学生id 和小组id传进去 用一个studentScore的对象去接
        StudentScore studentScore=new StudentScore();
        studentScore.setStudentId(studentGroup.getStudentId());
        studentScore.setGroupProName(group.getGroupProName());
        studentScore.setGroupId(group.getGroupId());

        studentScoreMapper.updateApplyInfo(studentScore);
    }

    @Transactional
    @Override
    public void enterNext(Integer studentId) {
        StudentScore studentScore=studentScoreMapper.selectByStudentId(studentId);
        Byte finish = studentScore.getStudentFinish();
        if (finish != null) {
            Byte newFinish = (byte) (finish + 1);
            studentScore.setStudentFinish(newFinish);
            studentScoreMapper.updateFinishByStudentId(studentScore);
        } else {
            // 处理null的情况
            log.info("学生完成阶段数据异常");
        }
    }

    @Transactional
    @Override
    public PageBeanStudentScore page(Integer page, Integer pageSize, String classId) {
        //设置分页参数
        PageHelper.startPage(page,pageSize);
        //执行查询 根据姓名进行查询 将这个姓名相关的人全部查询出来
        List<StudentScore> list=studentScoreMapper.searchByPageAndId(classId);

        // 创建 PageInfo 对象 用于分页
        PageInfo<StudentScore> pageInfo = new PageInfo<>(list);


        return new PageBeanStudentScore(pageInfo.getList(),(long)pageInfo.getTotal());
    }

    @Transactional
    @Override
    public StudentScore searchByStudentId(Integer studentId) {
        return studentScoreMapper.selectByStudentId(studentId);
    }

    @Transactional
    @Override
    public PageBeanStudentScore pageGroup(Integer page, Integer pageSize, String groupId) {
        //设置分页参数
        PageHelper.startPage(page,pageSize);
        //执行查询 根据姓名进行查询 将这个姓名相关的人全部查询出来
        List<StudentScore> list=studentScoreMapper.searchByPageAndGroupId(groupId);

        // 创建 PageInfo 对象 用于分页
        PageInfo<StudentScore> pageInfo = new PageInfo<>(list);


        return new PageBeanStudentScore(pageInfo.getList(),(long)pageInfo.getTotal());

    }

    @Transactional
    @Override
    public void goBack(Integer studentId) {
        StudentScore studentScore=studentScoreMapper.selectByStudentId(studentId);
        Byte finish = studentScore.getStudentFinish();
        if (finish != null) {
            Byte newFinish = (byte) (finish - 1);
            studentScore.setStudentFinish(newFinish);
            studentScoreMapper.updateFinishByStudentId(studentScore);
        } else {
            // 处理null的情况
            log.info("学生完成阶段数据异常");
        }
    }
}
