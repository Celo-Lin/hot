package com.hot.modules.hot.controller;

import com.github.pagehelper.PageHelper;
import com.hot.common.page.PageInfo;
import com.hot.common.result.Result;
import com.hot.common.result.ResultCode;
import com.hot.common.util.UserUtils;
import com.hot.modules.hot.entity.Hot;
import com.hot.modules.hot.entity.HotBasic;
import com.hot.modules.hot.service.HotService;
import com.hot.modules.sys.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/hot")
public class HotController {

    @Autowired
    private HotService hotService;

    @PostMapping("/get")
    public Result<?> get(@RequestParam Integer id) {
        Hot m = hotService.get(id);
        return m == null ? Result.of(ResultCode.FAIL) : Result.success(m);
    }

    @PostMapping("/list")
    public Result<?> list(@ModelAttribute Hot hot) {
        int p = hot.getPageNum() == null || hot.getPageNum() < 1 ? 1 : hot.getPageNum();
        int s = hot.getPageSize() == null || hot.getPageSize() < 1 ? 10 : hot.getPageSize();
        PageHelper.startPage(p, s);
        return Result.success(new PageInfo<>(hotService.list(hot)));
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody Hot hot, HttpServletRequest request) throws Exception {
        SysUser user = currentUser(request);
        hot.setCreateUser(user.getId());
        hot.setUpdateUser(user.getId());
        return hotService.save(hot,user.getId()) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    @PostMapping("/state")
    public Result<?> state(@ModelAttribute Hot hot, HttpServletRequest request) throws Exception {
        SysUser user = currentUser(request);
        hot.setCreateUser(user.getId());
        hot.setUpdateUser(user.getId());
        return hotService.state(hot) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }


    private SysUser currentUser(HttpServletRequest request) throws Exception {
        SysUser user = UserUtils.getCurrentUser(request);
        if (user == null) {
            throw new Exception("用户未登录");
        }
        return user;
    }
}
