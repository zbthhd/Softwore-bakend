package com.example.management_platform.controller;

import com.example.management_platform.common.R;
import com.example.management_platform.entity.Group;
import com.example.management_platform.service.impl.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/group")
@Slf4j
public class GroupController {

    @Autowired
    private GroupService groupService;


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

    @PostMapping("/add-gitee-url")
    public R<String> addGiteeUrl(@RequestBody Group group) {
        //修改小组的url

        //修改这个组所以学生URL
        groupService.addGiteeUrl(group);
        return R.success("上传成功");
    }

    @GetMapping("/get-group-info")
    public R<Group> getGroupInfo(@RequestParam("studentId") Integer studentId) {
        Group group = groupService.getGroupInfoByStudentId(studentId);
        return R.success(group);
    }
}
