package com.hot.modules.sys.controller;

import com.hot.common.exception.BizException;
import com.hot.common.result.Result;
import com.hot.common.result.ResultCode;
import com.hot.common.util.UserUtils;
import com.hot.modules.sys.entity.SysUser;
import com.hot.modules.sys.service.SysUserService;
import com.hot.common.page.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Autowired
    private SysUserService userService;

    /** 查：按 id 查询单个用户。 */
    @PostMapping("/get")
    public Result<SysUser> get(@ModelAttribute SysUser sysUser) {
        SysUser user = userService.get(sysUser);
        if (user == null) {
            return Result.of(ResultCode.FAIL);
        }
        return Result.success(user);
    }

    /** 查：用户分页列表，按 pageNum/pageSize 分页，可按 name/loginname 等模糊过滤。 */
    @PostMapping("/list")
    public Result<PageInfo<SysUser>> list(@ModelAttribute SysUser sysUser) {
        return Result.success(userService.list(sysUser));
    }

    /** 增 / 改：无 id 为新增，有 id 为修改。 */
    @PostMapping("/save")
    public Result<Void> save(HttpServletRequest request, @ModelAttribute SysUser sysUser) {
        SysUser u = UserUtils.getCurrentUser(request);
        if (u == null) return Result.of(ResultCode.NO_LOGIN);

        sysUser.setCreateUser(u.getId());
        int rows = userService.save(sysUser);
        return rows > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    /** 删：按 id 删除。 */
    @PostMapping("/delete")
    public Result<Void> delete(HttpServletRequest request, @RequestParam String id) {
        if (!StringUtils.hasText(id)) {
            throw new BizException(ResultCode.FAIL, "id 不能为空");
        }
        //获取当前登录的用户
        SysUser user = UserUtils.getCurrentUser(request);
        user.setId(id);
        int rows = userService.delete(user);
        return rows > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    /** 保证登录名唯一 */
    @PostMapping("/check")
    public Result<Boolean> check(@ModelAttribute SysUser sysUser) {
        SysUser user = userService.getByLoginname(sysUser.getLoginname());
        if (user == null) {
            return Result.success(true);
        }
        String id = sysUser.getId();
        String uid = user.getId();
        if (id != null && id.equals(uid)) {
            return Result.success(true);
        }
        return Result.success(false);
    }
}
