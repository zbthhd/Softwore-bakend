package com.example.management_platform.service.impl;

import com.example.management_platform.entity.ClassInfo;
import com.example.management_platform.entity.PageBeanClasses;
import com.example.management_platform.mapper.ClassMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClassService implements com.example.management_platform.service.ClassService {
    @Autowired
    private ClassMapper classMapper;


    @Transactional
    @Override
    public void create(ClassInfo classInfo) {
        classMapper.insert(classInfo);
        return;
    }

    @Override
    public List<ClassInfo> getClasses() {


        return classMapper.getClasses();
    }

    @Override
    public PageBeanClasses page(Integer page, Integer pageSize, String name) {
        //设置分页参数
        PageHelper.startPage(page,pageSize);
        //执行查询 根据姓名进行查询 将这个姓名相关的人全部查询出来
        List<ClassInfo> list=classMapper.searchByPageAndName(name);

        // 创建 PageInfo 对象 用于分页
        PageInfo<ClassInfo> pageInfo = new PageInfo<>(list);

        return new PageBeanClasses(pageInfo.getList(),(long)pageInfo.getTotal());

    }

    @Override
    public void deleteClass(Integer classId) {
        classMapper.deleteById(classId);

    }
}
