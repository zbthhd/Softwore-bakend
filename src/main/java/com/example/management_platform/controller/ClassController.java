package com.example.management_platform.controller;


import com.example.management_platform.common.R;
import com.example.management_platform.entity.ClassInfo;
import com.example.management_platform.entity.Group;
import com.example.management_platform.entity.PageBeanClasses;
import com.example.management_platform.entity.StudentGroup;
import com.example.management_platform.service.ClassService;
import com.example.management_platform.service.StudentGroupService;
import com.example.management_platform.service.StudentScoreService;
import com.example.management_platform.service.StudentService;
import com.example.management_platform.service.impl.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/class")
@Slf4j
public class ClassController {
    @Autowired
    private ClassService classService;


    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentScoreService studentScoreService;

    @Autowired
    private StudentGroupService studentGroupService;
    @Autowired
    private GroupService groupService;


    /**
     * 创建班级的接口
     * @param classInfo
     * @return
     */
    @PostMapping("/create_class")
    public R<String> createClass(@RequestBody ClassInfo classInfo) {
        classService.create(classInfo);
        return R.success("增加成功");
    }

    @GetMapping("/get_classes")
    public R<List<ClassInfo>> getClasses() {
        List<ClassInfo> list=new ArrayList<>();
        list=classService.getClasses();

        return R.success(list);
    }


    /**
     * 对班级的信息进行分页展示 并且满足按班级名进行查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<PageBeanClasses> page(@RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                   @RequestParam(defaultValue = "") String name
    ){
        log.info("我想看的是第{}页，每页总共{}个数据，要查得班级名为**{}**",page,pageSize,name);
        PageBeanClasses pageBeanClasses=classService.page(page,pageSize,name);

        return R.success(pageBeanClasses);
    }


    /**
     * 根据班级ID 删除班级 并将这个班级所有的学生的信息进行删除
     * @param classId
     * @return
     */
    @DeleteMapping("/delete_class/{classId}")
    public R<String> deleteClass( @PathVariable Integer classId) {
        //删除班级表里面的信息
        classService.deleteClass(classId);
        log.info("删除班级表里面的信息成功");

        //根据课程的ID删除学生表里面的信息
        studentService.deleteByClassId(classId);

        //根据课程ID删除小组表里面信息
       groupService.deleteByClassId(classId);

        //根据课程ID删除学生小组表里面信息
        studentGroupService.deleteByClassId(classId);

        //根据课程ID删除学生分数组表里面信息
        studentScoreService.deleteByClassId(classId);



        return R.success("删除成功");
    }


    @GetMapping("/get_class_groups/{classId}")
    public R<List<Group>> getClassGroups(@PathVariable Integer classId) {
        List<Group> list =new ArrayList<>();
        list=groupService.getGroupByClassId(classId);
        return R.success(list);
    }
}
