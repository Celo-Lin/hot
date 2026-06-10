package com.hot.modules.hot.controller;

import com.github.pagehelper.PageHelper;
import com.hot.common.page.PageInfo;
import com.hot.common.result.Result;
import com.hot.common.result.ResultCode;
import com.hot.common.util.UserUtils;
import com.hot.modules.hot.entity.HotCoop;
import com.hot.modules.hot.service.HotCoopService;
import com.hot.modules.sys.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/hot/coop")
public class HotCoopController {

    @Autowired
    private HotCoopService coopService;

    @PostMapping("/get")
    public Result<?> get(@RequestParam Integer id) {
        HotCoop m = coopService.get(id);
        return m == null ? Result.of(ResultCode.FAIL) : Result.success(m);
    }

    @PostMapping("/list")
    public Result<?> list(@ModelAttribute HotCoop coop) {
        int p = coop.getPageNum() == null || coop.getPageNum() < 1 ? 1 : coop.getPageNum();
        int s = coop.getPageSize() == null || coop.getPageSize() < 1 ? 10 : coop.getPageSize();
        PageHelper.startPage(p, s);
        return Result.success(new PageInfo<>(coopService.list(coop)));
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody HotCoop coop, HttpServletRequest request) throws Exception {
        SysUser user = currentUser(request);
        coop.setCreateUser(user.getId());
        coop.setUpdateUser(user.getId());
        coop.setBdName(user.getName());
        if (coop.getDocNum() == null || coop.getDocNum().trim().length() == 0) {
            coop.setDocNum("COOP"+System.nanoTime());
        }
        return coopService.save(coop) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    @PostMapping("/delete")
    public Result<?> delete(@RequestParam Integer id, HttpServletRequest request) throws Exception {
        SysUser user = currentUser(request);
        return coopService.delete(id, user.getId()) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    @PostMapping("/close")
    public Result<?> close(@RequestParam Integer id, HttpServletRequest request) throws Exception {
        SysUser user = currentUser(request);
        return coopService.close(id, user.getId()) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    private SysUser currentUser(HttpServletRequest request) throws Exception {
        SysUser user = UserUtils.getCurrentUser(request);
        if (user == null) {
            throw new Exception("用户未登录");
        }
        return user;
    }
}
