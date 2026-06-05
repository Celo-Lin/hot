package com.hot.modules.sys.controller;

import com.github.pagehelper.PageHelper;
import com.hot.common.page.PageInfo;
import com.hot.common.result.Result;
import com.hot.common.result.ResultCode;
import com.hot.common.util.UserUtils;
import com.hot.modules.sys.entity.SysModu;
import com.hot.modules.sys.entity.SysRole;
import com.hot.modules.sys.entity.SysUser;
import com.hot.modules.sys.service.SysModuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/sys/modu")
public class SysModuController {

    @Autowired
    private SysModuService moduService;

    @PostMapping("/get")
    public Result<?> get(@RequestParam String id) {
        SysModu m = moduService.get(id);
        return m == null ? Result.of(ResultCode.FAIL) : Result.success(m);
    }

    @PostMapping("/list")
    public Result<?> list(@ModelAttribute SysModu modu) {
        startPage(modu.getPageNum(), modu.getPageSize());
        return Result.success(new PageInfo<>(moduService.list(modu)));
    }

    @PostMapping("/menuModu")
    public Result<?> menuModu() {
        return Result.success(moduService.menuModu());
    }

    @PostMapping("/save")
    public Result<?> save(HttpServletRequest request, @ModelAttribute SysModu modu) {
        modu.setCreateUser(currentUserId(request));
        return moduService.save(modu) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    @PostMapping("/delete")
    public Result<?> delete(HttpServletRequest request, @RequestParam Integer id) {
        SysModu modu = new SysModu();
        modu.setId(id);
        modu.setCreateUser(currentUserId(request));
        return moduService.delete(modu) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    // 权限
    @PostMapping("/roleget")
    public Result<?> roleget(@RequestParam String id) {
        SysRole m = moduService.roleget(id);
        return m == null ? Result.of(ResultCode.FAIL) : Result.success(m);
    }

    @PostMapping("/rolelist")
    public Result<?> rolelist(@ModelAttribute SysRole role) {
        startPage(role.getPageNum(), role.getPageSize());
        return Result.success(new PageInfo<>(moduService.rolelist(role)));
    }

    @PostMapping("/rolesave")
    public Result<?> rolesave(HttpServletRequest request, @ModelAttribute SysRole role) {
        role.setCreateUser(currentUserId(request));
        return moduService.rolesave(role) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    @PostMapping("/roledelete")
    public Result<?> roledelete(HttpServletRequest request, @RequestParam Integer id) {
        SysRole role = new SysRole();
        role.setId(id);
        role.setCreateUser(currentUserId(request));
        return moduService.roledelete(role) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
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
