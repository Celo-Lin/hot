package com.hot.modules.sys.controller;

import com.github.pagehelper.PageHelper;
import com.hot.common.page.PageInfo;
import com.hot.common.result.Result;
import com.hot.common.result.ResultCode;
import com.hot.common.util.UserUtils;
import com.hot.modules.sys.entity.SysMenu;
import com.hot.modules.sys.entity.SysRole;
import com.hot.modules.sys.entity.SysUser;
import com.hot.modules.sys.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/sys/menu")
public class SysMenuController {

    @Autowired
    private SysMenuService menuService;

    @PostMapping("/get")
    public Result<?> get(@RequestParam String id) {
        SysMenu m = menuService.get(id);
        return m == null ? Result.of(ResultCode.FAIL) : Result.success(m);
    }

    /** 菜单树。 */
    @PostMapping("/list")
    public Result<?> list() {
        return Result.success(menuService.list());
    }

    @PostMapping("/save")
    public Result<?> save(HttpServletRequest request, @ModelAttribute SysMenu menu) {
        menu.setCreateUser(currentUserId(request));
        return menuService.save(menu) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    @PostMapping("/delete")
    public Result<?> delete(HttpServletRequest request, @RequestParam Integer id) {
        SysMenu menu = new SysMenu();
        menu.setId(id);
        menu.setCreateUser(currentUserId(request));
        return menuService.delete(menu) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    // 权限
    @PostMapping("/roleget")
    public Result<?> roleget(@RequestParam String id) {
        SysRole m = menuService.roleget(id);
        return m == null ? Result.of(ResultCode.FAIL) : Result.success(m);
    }

    @PostMapping("/rolelist")
    public Result<?> rolelist(@ModelAttribute SysRole role) {
        startPage(role.getPageNum(), role.getPageSize());
        return Result.success(new PageInfo<>(menuService.rolelist(role)));
    }

    @PostMapping("/rolesave")
    public Result<?> rolesave(HttpServletRequest request, @ModelAttribute SysRole role) {
        role.setCreateUser(currentUserId(request));
        return menuService.rolesave(role) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    @PostMapping("/roledelete")
    public Result<?> roledelete(HttpServletRequest request, @RequestParam Integer id) {
        SysRole role = new SysRole();
        role.setId(id);
        role.setCreateUser(currentUserId(request));
        return menuService.roledelete(role) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    private void startPage(Integer pageNum, Integer pageSize) {
        int p = pageNum == null || pageNum < 1 ? 1 : pageNum;
        int s = pageSize == null || pageSize < 1 ? 10 : pageSize;
        PageHelper.startPage(p, s);
    }

    private String currentUserId(HttpServletRequest request) {
        SysUser user = UserUtils.getCurrentUser(request);
        return user == null ? null : user.getId();
    }
}
