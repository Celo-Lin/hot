package com.hot.modules.sys.controller;

import com.hot.common.constant.AuthConstant;
import com.hot.common.result.Result;
import com.hot.common.result.ResultCode;
import com.hot.common.util.StringUtils;
import com.hot.common.util.UserUtils;
import com.hot.modules.sys.entity.SysUser;
import com.hot.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录鉴权接口：登录、注册、退出。
 * /login、/register 已在 WebMvcConfig 中放行，无需令牌即可访问。
 */
@RestController
public class AuthController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/login")
    public Result<String> login(HttpServletRequest request,
            @RequestParam(required = true) String loginname,
            @RequestParam(required = true) String password) {
        String token = sysUserService.login(loginname, password, StringUtils.getRemoteAddr(request));
        return Result.success(token);
    }

    @PostMapping("/logout")
    public Result<Void> logout(@RequestHeader(value = AuthConstant.TOKEN_HEADER, required = false) String token) {
        sysUserService.logout(token);
        return Result.success();
    }

    /**
     * 修改密码：需登录，校验原密码后更新。成功后令牌失效，需用新密码重新登录。
     */
    @PostMapping("/password")
    public Result<Void> password(HttpServletRequest request,
            @RequestHeader(value = AuthConstant.TOKEN_HEADER, required = false) String token,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        SysUser current = UserUtils.getCurrentUser(request);
        if (current == null) {
            return Result.of(ResultCode.NO_LOGIN);
        }
        sysUserService.updatePassword(current.getLoginname(), oldPassword, newPassword);
        // 改密后令牌失效，强制重新登录
        sysUserService.logout(token);
        return Result.success();
    }

    /** 查：获取当前登录用户（由拦截器写入 request 中的用户信息）。 */
    @PostMapping("/current")
    public Result<SysUser> current(HttpServletRequest request) {
        SysUser user = UserUtils.getCurrentUser(request);
        if (user == null) {
            return Result.of(ResultCode.NO_LOGIN);
        }
        return Result.success(user);
    }
}
