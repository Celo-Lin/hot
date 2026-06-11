package com.hot.modules.hot.controller;

import com.github.pagehelper.PageHelper;
import com.hot.common.page.PageInfo;
import com.hot.common.result.Result;
import com.hot.common.result.ResultCode;
import com.hot.common.util.UserUtils;
import com.hot.modules.hot.entity.Project;
import com.hot.modules.hot.service.ProjectService;
import com.hot.modules.sys.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/get")
    public Result<?> get(@RequestParam Integer id) {
        Project m = projectService.get(id);
        return m == null ? Result.of(ResultCode.FAIL) : Result.success(m);
    }

    @PostMapping("/list")
    public Result<?> list(@ModelAttribute Project project) {
        int p = project.getPageNum() == null || project.getPageNum() < 1 ? 1 : project.getPageNum();
        int s = project.getPageSize() == null || project.getPageSize() < 1 ? 10 : project.getPageSize();
        PageHelper.startPage(p, s);
        return Result.success(new PageInfo<>(projectService.list(project)));
    }

    @PostMapping("/save")
    public Result<?> save(@ModelAttribute Project project, HttpServletRequest request) {
        String uid = currentUserId(request);
        project.setCreateUser(uid);
        project.setUpdateUser(uid);
        return projectService.save(project) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    @PostMapping("/delete")
    public Result<?> delete(@ModelAttribute Project project, HttpServletRequest request) {
        String uid = currentUserId(request);
        project.setCreateUser(uid);
        project.setUpdateUser(uid);
        return projectService.delete(project) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    //批量关闭
    @PostMapping("/close")
    public Result<?> close(@RequestParam String[] ids, HttpServletRequest request) {
        String uid = currentUserId(request);
        return projectService.close(ids, uid) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    private String currentUserId(HttpServletRequest request) {
        SysUser user = UserUtils.getCurrentUser(request);
        return user == null ? null : user.getId();
    }
}
