package com.example.management_platform.controller;

import com.example.management_platform.common.R;
import com.example.management_platform.dto.StudentDto;
import com.example.management_platform.dto.StudentGroupDto;
import com.example.management_platform.dto.StudentUploadDto;
import com.example.management_platform.entity.*;
import com.example.management_platform.mapper.ClassMapper;
import com.example.management_platform.service.StudentGroupService;
import com.example.management_platform.service.impl.ClassService;
import com.example.management_platform.service.impl.GroupService;
import com.example.management_platform.service.impl.StudentScoreService;
import com.example.management_platform.service.impl.StudentService;
import com.example.management_platform.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentGroupService studentGroupService;

    @Autowired
    private StudentScoreService studentScoreService;

    @Autowired
    private StudentService studentService;
    @Autowired
    private GroupService groupService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private ClassService classService;

    @Autowired
    private JwtUtils jwtUtils;
    /**
     * 进行学生注册
     * @param studentDto
     * @return
     */
    @PostMapping("/register")
    public R<String> register(@RequestBody StudentDto studentDto) {
        //判断这个用户名是否已经存在
        Student student = studentService.searchByStudentUsername(studentDto.getStudentUsername());
        if(student!=null){
            return R.error("用户名已存在 请重新选择用户名");
        }

        //判断验证码是否正确
        String code=null;
        code=stringRedisTemplate.opsForValue().get(studentDto.getStudentEmail());
        // 检查验证码是否为空
        if (code==null||code.isEmpty()) {
            return R.error("验证码不存在或已过期，请重新发送验证码");
        }

        if(!code.equals(studentDto.getVerificationCode())){
            return R.error("验证码错误 请核对后重新输入");
        }

        try {
            // 注册用户
            studentService.create(studentDto);
            return R.success("创建成功");
        } catch (Exception e) {
            log.error("注册失败: {}", e.getMessage(), e);
            return R.error("注册失败，请稍后再试");
        }

    }

    @PostMapping("/login")
    public R<String> login(@RequestBody Student student) {

            // 尝试通过用户名和密码查找学生信息
            Student result = studentService.searchByUsernameAndPassword(student);
            if(result == null){
                return R.error("用户名或者密码不存在 请核对");
            }
        // 创建JWT 放ID 用户名 以及职位
        Map<String,Object> cliams = new HashMap<>();
        cliams.put("studentId",result.getStudentId());
        cliams.put("adminUsername",result.getStudentUsername());
        cliams.put("role","student");

        String jwt = jwtUtils.generateJwt(cliams);
        log.info("登录的学生信息为：{}",result);
        return R.success(jwt);

    }

    /**
     * 将学生踢出小组
     * @param studentId
     * @return 操作的学生
     */
    @PutMapping("/delete/{studentId}")
    public R<Student> deleteStudent(@PathVariable("studentId") Integer studentId) {
        //修改学生小组表
        studentGroupService.expelFromGroup(studentId);
        log.info("学生小组表修改完毕");

        //修改学生得分表
        studentScoreService.expelFromGroup(studentId);
        log.info("学生得分表修改完毕");


        //找到这个学生信息返回给前端
        return R.success(studentService.selectUserById(studentId));
    }

    /**
     * 根据组长的ID 去解散小组 并修改该组学生的相关信息
     * @param studentId
     * @return
     */
    @Transactional
    @DeleteMapping("/delete-group")
    public R<Student> deleteGroup(@RequestParam("studentId") Integer studentId){
        try {
            // 先根据这个组长的id找到这个小组对象 再根据对象找到ID
            Group group = groupService.searchByStudentId(studentId);

            // 根据这个小组id去找到学生小组信息表中的数据 存到list里面
            List<StudentGroup> list = studentGroupService.searchAllByGroupId(group.getGroupId());

            // 根据这个list中的信息去修改学生小组表和学生得分表
            studentGroupService.updateByList(list);
            // 上面这个直接改两个表的数据

            // 根据这个组长的ID去删除这个组的信息
            groupService.deleteByStudentId(studentId);
            log.info("根据组长id删除小组成功");

            return R.success(studentService.selectUserById(studentId));
        } catch (Exception e) {
            log.error("删除小组时发生错误: ", e);
            throw new RuntimeException("删除小组失败", e); // 异常抛出会触发事务回滚
        }
    }

    /**
     * 学生忘记密码时 用邮箱和验证码找回修改密码
     * @param studentDto
     * @return
     */
    @PostMapping("/forget-password")
    public R<String> forgetPassword(@RequestBody StudentDto studentDto) {
        //先去判断验证码是否正确
        //判断验证码是否正确
        String code=null;
        code=stringRedisTemplate.opsForValue().get(studentDto.getStudentEmail());
        // 检查验证码是否为空
        if (code==null||code.isEmpty()) {
            return R.error("验证码不存在或已过期，请重新发送验证码");
        }

        if(!code.equals(studentDto.getVerificationCode())){
            return R.error("验证码错误 请核对后重新输入");
        }

        try {
            Student student=studentService.searchByEmailAndUsername(studentDto);
            if(student==null){
                return R.error("用户名和邮箱不对 请重新输入");
            }
            studentService.findBack(studentDto);
            return R.success("找回成功");
        } catch (Exception e) {
            log.error("注册失败: {}", e.getMessage(), e);
            return R.error("找回密码失败，请稍后再试");
        }
        //看下邮箱和用户名是否有这个人
    }

    /**
     * 申请加入小组
     * @param studentGroup
     * @return
     */
    @PostMapping("/enter-group")
    public R<String> enterGroup(@RequestBody StudentGroup studentGroup) {
        //修改学生小组表的信息
        studentGroupService.applyGroupByGroupId(studentGroup);
        log.info("修改学生小组信息成功");

        //修改学生成绩表的信息
        studentScoreService.applyGroupByGroupId(studentGroup);
        log.info("修改学生成绩表成功");

        return R.success("申请以发出 等待通知");
    }

    /**
     * 学生更新个人信息
     * @param studentDto
     * @return
     */
    @PostMapping("/update")
    public R<Student> update(@RequestBody StudentDto studentDto) {
        //先根据这个这个studentUsername去找到这个学生验证原密码是否正确
        Student student = studentService.searchByStudentUsername(studentDto.getStudentUsername());
        if(student==null){
            return R.error("用户名不存在");
        }
        //把原始密码进行MD5加密 加密后对边数据库中的密码
        String studentPassword = studentDto.getStudentPasswordPre();
        String s = DigestUtils.md5DigestAsHex(studentPassword.getBytes());
        log.info(s);
        if(!s.equals(student.getStudentPassword())){
            return R.error("原密码错误 请核对后重新输入");
        }
        studentService.updateInfo(studentDto);
        log.info("修改学生信息表完毕");
        return R.success(studentService.searchByStudentUsername(studentDto.getStudentUsername()));
    }


    /**
     * 获取小组信息 并进行分页展示
     * @param studentId
     * @return
     */
    @GetMapping("/group-info/page")
    public R<Object> page(@RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer pageSize,
                                        @RequestParam("studentId") Integer studentId
    ){
        log.info("我想看的是第{}页，每页总共{}个数据,这个学生的ID为{}",page,pageSize,studentId);
        //先根据这个学生的ID找到它的组
        StudentGroup studentGroup=studentGroupService.searchByStudentId(studentId);
        if(studentGroup.getGroupId()==-1){
            return R.success("未加入小组 不用渲染数据");
        }
        //创建返回的对象
        PageBeanStudentGroup pageBeanStudentGroup=studentGroupService.page(page,pageSize,studentGroup.getGroupId());


        return R.success(pageBeanStudentGroup);
    }

    /**
     * 学生自己的项目提交文件后进入下一阶段
     * @param studentId
     * @return
     */
    @PutMapping("/enter-next")
    public R<String> enterNext(@RequestParam("studentId") Integer studentId) {

        //学生得分信息进入下一个阶段
        studentScoreService.enterNext(studentId);

        return R.success("进入下一阶段成功");
    }

    /**
     * 获取个人项目信息
     * @param studentId
     * @return
     */
    @GetMapping("/get-individual-pro")
    public R<StudentGroupDto> getIndividualPro(@RequestParam("studentId") Integer studentId) {


        return R.success(studentGroupService.searchByStudentId(studentId));

    }



    @PostMapping("/upload")
    public R<String> upload(@ModelAttribute StudentUploadDto studentUploadDto) throws IOException {
        //先判断一下这个人存不存在
        Student student=studentService.searchByStudentId(studentUploadDto.getStudentId());
        Group group=groupService.searchByStudentId(student.getStudentId());
        if(student==null||group==null){
            return R.error("学生或者小组不存在");
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
            else{
                log.info("班级{}已存在",className);
            }

            //为这个小组创建文件夹
            //先设置学生文件夹存在的路径
            String filName=group.getGroupName()+"+"+group.getGroupProName();

            Path groupFolderPath=Paths.get(String.valueOf(classFolderPath),filName);
            if(!Files.exists(groupFolderPath)){
                Files.createDirectories(groupFolderPath);
                log.info(" 小组文件夹 {} 创建文件夹成功", filName);
            }
            else {
                log.info("小组文件夹{}已存在",filName);
            }

            //为这个学生创建文件夹
            //先设置学生文件夹存在的路径
            filName=student.getStudentName()+"+"+student.getStudentNumber();
            Path studentFolderPath=Paths.get(String.valueOf(groupFolderPath),filName);
            if(!Files.exists(studentFolderPath)){
                Files.createDirectories(studentFolderPath);
                log.info(" 学生文件夹 {} 创建文件夹成功", filName);
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

            while (Files.exists(studentFolderPath.resolve(fileName))) {
                // 如果文件已存在，则添加时间戳或递增数字以保证唯一性
                fileName = namePart + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + extension;
                log.info("重名文件上传");
            }

            // 记录存储的路径，包括学生文件夹
            Path filePath = studentFolderPath.resolve(fileName);
            Files.write(filePath, file.getBytes());

            return R.success(fileName);
        }

        return R.error("上传失败 请重新上传");
    }
}
