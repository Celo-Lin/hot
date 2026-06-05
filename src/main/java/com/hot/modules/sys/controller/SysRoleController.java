package com.hot.modules.sys.controller;

import com.github.pagehelper.PageHelper;
import com.hot.common.page.PageInfo;
import com.hot.common.result.Result;
import com.hot.common.result.ResultCode;
import com.hot.common.util.UserUtils;
import com.hot.modules.sys.entity.SysRole;
import com.hot.modules.sys.entity.SysUser;
import com.hot.modules.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/sys/role")
public class SysRoleController {

    @Autowired
    private SysRoleService roleService;

    @PostMapping("/get")
    public Result<?> get(@RequestParam String id) {
        SysRole m = roleService.get(id);
        return m == null ? Result.of(ResultCode.FAIL) : Result.success(m);
    }

    @PostMapping("/list")
    public Result<?> list(@ModelAttribute SysRole role) {
        int p = role.getPageNum() == null || role.getPageNum() < 1 ? 1 : role.getPageNum();
        int s = role.getPageSize() == null || role.getPageSize() < 1 ? 10 : role.getPageSize();
        PageHelper.startPage(p, s);
        return Result.success(new PageInfo<>(roleService.list(role)));
    }

    @PostMapping("/save")
    public Result<?> save(HttpServletRequest request, @ModelAttribute SysRole role) {
        role.setCreateUser(currentUserId(request));
        return roleService.save(role) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    @PostMapping("/delete")
    public Result<?> delete(HttpServletRequest request, @RequestParam Integer id) {
        SysRole role = new SysRole();
        role.setId(id);
        role.setCreateUser(currentUserId(request));
        return roleService.delete(role) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    private String currentUserId(HttpServletRequest request) {
        SysUser user = UserUtils.getCurrentUser(request);
        return user == null ? null : user.getId();
    }
}
