package com.example.management_platform.controller;

import com.example.management_platform.common.R;
import com.example.management_platform.dto.StudentUploadDto;
import com.example.management_platform.entity.ClassInfo;
import com.example.management_platform.entity.Group;
import com.example.management_platform.entity.Student;
import com.example.management_platform.service.impl.ClassService;
import com.example.management_platform.service.impl.GroupService;
import com.example.management_platform.service.impl.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/group")
@Slf4j
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private ClassService classService;

    @Autowired
    private StudentService studentService;
    /**
     * 根据小组的id查询小组的基本信息
     * @param groupId
     * @return
     */
    @GetMapping("/get-group-info/{groupId}")
    public R<Group> getGroupInfo(@PathVariable int groupId) {
        Group group =new Group();
        group=groupService.getGroupInfoByGroupId(groupId);
        return R.success(group);
    }

    @PostMapping("/project-evaluation-approval")
    public R<String> projectEvaluationApproval(@RequestBody Group group) {
        groupService.evaluationApproval(group);
        return R.success("打分立项成功");
    }

    @PutMapping("/enter-next")
    public R<String> enterNext(@RequestParam("studentId") Integer studentId) {

        groupService.enterNext(studentId);
        return R.success("小组成功进入下一阶段");
    }

    @PutMapping("/go-back")
    public R<String> goBack(@RequestParam("studentId") Integer studentId) {

        groupService.goBack(studentId);
        return R.success("小组回到上一阶段");
    }


    @PostMapping("/add-gitee-url")
    public R<String> addGiteeUrl(@RequestBody Group group) {
        //修改小组的url

        //修改这个组所以学生URL
        groupService.addGiteeUrl(group);
        return R.success("上传成功");
    }

    /**
     * 根据学生id获取小组的信息
     * @param studentId
     * @return
     */
    @GetMapping("/get-group-info")
    public R<Group> getGroupInfo(@RequestParam("studentId") Integer studentId) {
        Group group = groupService.getGroupInfoByStudentId(studentId);
        return R.success(group);
    }

    @PostMapping("/upload")
    public R<String> upload(@ModelAttribute StudentUploadDto studentUploadDto) throws IOException {
        //先判断一下这小组存不存在
        Group group=groupService.searchByStudentId(studentUploadDto.getStudentId());
        if(group==null){
            return R.error("小组不存在");
        }
        log.info("要上传的文件的名字为：{}",studentUploadDto.getFile().toString());
        MultipartFile file = studentUploadDto.getFile();
        if(!file.isEmpty()) {

            //先根据它的classId去找到班级名字
            ClassInfo classInfo = classService.getClassNameByClassId(group.getClassId());
            String className=classInfo.getClassName();
            //先构建一个班级文件夹路径
            Path classFolderPath= Paths.get("C:\\Users\\15117\\Desktop\\软件工程",className);
            if(!Files.exists(classFolderPath)){
                Files.createDirectories(classFolderPath);
                log.info("为班级 {} 创建文件夹成功", className);
            }
            //为这个小组创建文件夹
            //先设置学生文件夹存在的路径
            String filName=group.getGroupName()+"+"+group.getGroupProName();
            Path grouptFolderPath=Paths.get(String.valueOf(classFolderPath),filName);
            if(!Files.exists(grouptFolderPath)){
                Files.createDirectories(grouptFolderPath);
                log.info(" 小组文件夹 {} 创建文件夹成功", filName);
            }
            else {
                log.info("文件夹{}已存在",filName);
            }
            //学生文件夹创建成功之后 将文件的信息存储进去
            // 获取原始文件名和扩展名
            String originalFilename = file.getOriginalFilename();
            log.info(originalFilename);
            int index = originalFilename.lastIndexOf(".");
            String namePart = (index > 0) ? originalFilename.substring(0, index) : originalFilename;
            String extension = (index > 0) ? originalFilename.substring(index) : "";


            // 确保文件名唯一
            String fileName = originalFilename;

            while (Files.exists(grouptFolderPath.resolve(fileName))) {
                // 如果文件已存在，则添加时间戳或递增数字以保证唯一性
                fileName = namePart + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + extension;
                log.info("重名文件上传");
            }

            // 记录存储的路径，包括学生文件夹
            Path filePath = grouptFolderPath.resolve(fileName);
            Files.write(filePath, file.getBytes());

            return R.success(fileName);
        }

        return R.error("上传失败 请重新上传");
    }

}
