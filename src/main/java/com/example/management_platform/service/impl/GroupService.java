package com.example.management_platform.service.impl;

import com.example.management_platform.common.R;
import com.example.management_platform.entity.Group;
import com.example.management_platform.entity.StudentGroup;
import com.example.management_platform.entity.StudentScore;
import com.example.management_platform.mapper.GroupMapper;
import com.example.management_platform.mapper.StudentGroupMapper;
import com.example.management_platform.utils.ExportExcel;
import jakarta.servlet.ServletOutputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

@Slf4j
@Service
public class GroupService implements com.example.management_platform.service.GroupService {

   @Autowired
   private GroupMapper groupMapper;
    @Autowired
    private StudentGroupMapper studentGroupMapper;

    @Override
    public List<Group> getGroupByClassId(Integer classId) {
        return groupMapper.selectByClassId(classId);
    }

    @Override
    public void deleteByClassId(Integer classId) {
        groupMapper.deleteByClassId(classId);
    }

    @Override
    public Group getGroupInfoByGroupId(int groupId) {
        return groupMapper.selectByGroupId(groupId);
    }

    @Override
    public void evaluationApproval(Group group) {
        groupMapper.updateByGroupId(group);
    }

    @Override
    public void exportExcel(ServletOutputStream outputStream, String title,Integer classId) {
        // 定义列标 就是一个Excel的每一个字段标题
        String[] rowsName = new String[]{"小组名", "项目名", "组长", "得分", "仓库地址"};
        // 创建导出数据集合 后续会将dataList中的数据写到Excel
        List<Object[]> dataList = new ArrayList<>();
        // 从数据库查询用户列表（需要的可以自行添加参数）

        List<Group> groupList = groupMapper.selectByClassId(classId);
        Group group = null;
        // 将列表信息封装到一个Object数组
        // 我这里封装Object数组 是为了方便后续代码复用,不会将对象类型写死
        for (int i=0; i<groupList.size(); i++){
            //将数据库查到的每条数据 循环封装到Object[]
            group=groupList.get(i);

            Object[] objs = new Object[]{group.getGroupName(),group.getGroupProName(),group.getGroupLeader(),group.getGroupScore(),group.getGiteeUrl()};
            //将转换好的数据 存入dataList
            dataList.add(objs);
        }
        // 创建ExportExcel工具类对象 通过构造方法赋值
        ExportExcel ex = new ExportExcel(title, rowsName, dataList);
        try {
            // 调用生成Excel的方法,将数据通过输出流写出
            ex.export(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.flush();
        out.close();
    }

    @Override
    public void deleteByStudentId(Integer studentId) {
        groupMapper.deleteByStudentId(studentId);
    }

    @Override
    public Group searchByStudentId(Integer studentId) {
        return groupMapper.selectByStudentId(studentId);
    }

    @Override
    public void enterNext(Integer studentId) {
        Group group=groupMapper.selectByStudentId(studentId);
        Byte finish = group.getGroupFinish();
        if (finish != null) {
            Byte newFinish = (byte) (finish + 1);
            group.setGroupFinish(newFinish);
            groupMapper.updateFinishByGroup(group);
        } else {
            // 处理null的情况
            log.info("学生完成阶段数据异常");
        }


    }

    @Override
    public void addGiteeUrl(Group group) {
        //先修改小组的url
        groupMapper.updateUrlByGroupId(group);
        //根据这个小组的ID 去修改学生小组表中的giteeUrl
        StudentGroup studentGroup=new StudentGroup();
        studentGroup.setGroupId(group.getGroupId());
        studentGroup.setGiteeUrl(group.getGiteeUrl());
        //开始修改
        studentGroupMapper.updateUrlByGroupId(studentGroup);

    }

    @Override
    public Group getGroupInfoByStudentId(Integer studentId) {
        //先通过学生小组信息表找到小组的ID 再根据小组ID找到小组信息即可
        StudentGroup studentGroup = studentGroupMapper.selectByStudentId(studentId);

       return groupMapper.selectByGroupId(studentGroup.getGroupId());

    }
}
