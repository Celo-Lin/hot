package com.hot.modules.sys.service;

import com.hot.common.constant.AuthConstant;
import com.hot.common.exception.BizException;
import com.hot.common.result.Result;
import com.hot.common.result.ResultCode;
import com.hot.common.util.PasswordUtil;
import com.hot.common.util.RedisUtils;
import com.hot.modules.sys.dao.SysUserDao;
import com.hot.modules.sys.entity.SysUser;
import com.github.pagehelper.PageHelper;
import com.hot.common.page.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class SysUserService {

    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private RedisUtils redisUtils;

    public SysUser get(SysUser sysUser) {
        SysUser user = sysUserDao.get(sysUser);
        if (user != null) {
            user.setPassword(null);
        }
        return user;
    }

    public PageInfo<SysUser> list(SysUser sysUser) {
        int pageNum = sysUser.getPageNum() == null || sysUser.getPageNum() < 1 ? 1 : sysUser.getPageNum();
        int pageSize = sysUser.getPageSize() == null || sysUser.getPageSize() < 1 ? 10 : sysUser.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<SysUser> users = sysUserDao.list(sysUser);
        return new PageInfo<>(users);
    }

    @Transactional
    public int save(SysUser sysUser) {
        try {
            redisUtils.delete(AuthConstant.USER_CACHE+sysUser.getId());
            redisUtils.delete(AuthConstant.MENU_CACHE+sysUser.getId());
            redisUtils.delete(AuthConstant.MODU_CACHE+sysUser.getId());
            redisUtils.delete(AuthConstant.BUTT_CACHE+sysUser.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String id = sysUser.getId();
        SysUser user = getByLoginname(sysUser.getLoginname());
        boolean b = user == null ? false : id == null ? true : !id.equals(user.getId());
        if (b) {
            throw new BizException(ResultCode.FAIL, "用户已存在");
        }
        String now = LocalDateTime.now().format(TIME_FMT);
        sysUser.setUpdateTime(now);
        // 有传密码时统一做 BCrypt 加密，保证新增/改密后的账号能正常登录
        if (StringUtils.hasText(sysUser.getPassword())) {
            sysUser.setPassword(PasswordUtil.encrypt(sysUser.getPassword()));
        }
        if (!StringUtils.hasText(sysUser.getId())) {
            sysUser.setId(UUID.randomUUID().toString().replace("-", ""));
            sysUser.setCreateTime(now);
            return sysUserDao.insert(sysUser);
        }
        return sysUserDao.update(sysUser);
    }

    @Transactional
    public int delete(SysUser user) {
        return sysUserDao.delete(user);
    }

    public SysUser getByLoginname(String loginname) {
        return sysUserDao.getByLoginname(loginname);
    }

    /**
     * 修改密码：校验原密码后，将新密码加密存库。
     * Redis 中缓存的用户密码已置空，故按 loginname 从库中取出含密码哈希的用户校验。
     */
    @Transactional
    public void updatePassword(String loginname, String oldPassword, String newPassword) {
        if (!StringUtils.hasText(oldPassword) || !StringUtils.hasText(newPassword)) {
            throw new BizException(ResultCode.FAIL, "原密码和新密码不能为空");
        }
        SysUser u = sysUserDao.getByLoginname(loginname);
        if (u == null) {
            throw new BizException(ResultCode.FAIL, "用户不存在");
        }
        if (!PasswordUtil.matches(oldPassword, u.getPassword())) {
            throw new BizException(ResultCode.FAIL, "原密码不正确");
        }
        SysUser update = new SysUser();
        update.setId(u.getId());
        update.setPassword(PasswordUtil.encrypt(newPassword));
        sysUserDao.updatePassword(update);
    }

    /**
     * 登录：校验账号密码，成功后生成令牌写入 Redis 并返回。
     */
    public String login(String loginname, String password, String ip) {
        SysUser u = sysUserDao.getByLoginname(loginname);
        if (u == null) {
            throw new BizException(ResultCode.LOGIN_FAIL);
        }
        // 用户锁定后，时间到了解锁
        if (u.getLoginflag().equals(2)) {
            long errorTime = u.getErrorTime().getTime();
            long nowTime = System.currentTimeMillis();
            long time = (nowTime - errorTime) / (1000 * 60);
            if (5 - time <= 0) {
                u.setErrorTime(null);
                u.setErrorCount(0);
                u.setLoginflag(1);
                sysUserDao.updateError(u);
            } else {
                throw new BizException(ResultCode.LOGIN_LOCK,"当前用户登录已锁定，请"+(5-time)+"分钟后重试");
            }
        }

        if (!PasswordUtil.matches(password, u.getPassword())) {
            // 密码错误次数
            int errorCount = u.getErrorCount();
            // 密码错误次数大于3次，锁定用户
            if (errorCount >= 2) {
                u.setLoginflag(2);
                u.setErrorTime(new Date());
                sysUserDao.updateError(u);
            } else {
                if (errorCount == 0) {
                    u.setErrorCount(1);
                    u.setErrorTime(new Date());
                    sysUserDao.updateError(u);
                } else {
                    long errorTime = u.getErrorTime().getTime();
                    long nowTime = System.currentTimeMillis();
                    long time = (nowTime - errorTime) / (1000 * 60);
                    if (time >= 1) {
                        u.setErrorTime(new Date());
                        sysUserDao.updateError(u);
                    } else {
                        u.setErrorCount(errorCount+1);
                        u.setErrorTime(new Date());
                        sysUserDao.updateError(u);
                    }
                }
            }
            throw new BizException(ResultCode.LOGIN_FAIL);
        }
        //生成 token
        String token = UUID.randomUUID().toString().replace("-", "");
        u.setLogindate(LocalDateTime.now().format(TIME_FMT));
        u.setLoginip(ip);
        // 更新登录信息
        sysUserDao.updateLogin(u);
        // 更新错误次数
        u.setErrorTime(null);
        u.setErrorCount(0);
        u.setLoginflag(1);
        sysUserDao.updateError(u);

        SysUser u2 = new SysUser();
        u2.setId(u.getId());
        u2.setName(u.getName());
        u2.setLoginname(u.getLoginname());
        u2.setPercode(u.getPercode());
        u2.setDepname(u.getDepname());
        u2.setUserType(u.getUserType());
        u2.setLogindate(u.getLogindate());
        u2.setMenurole(u.getMenurole());
        u2.setModurole(u.getModurole());
        u2.setButtrole(u.getButtrole());
        u2.setDatarole(u.getDatarole());
        u2.setButtlist(u.getButtlist());
        u2.setDatalist(u.getDatalist());
        u2.setMenulist(u.getMenulist());
        u2.setModulist(u.getModulist());

        redisUtils.set(AuthConstant.TOKEN_KEY_PREFIX + token, u2, AuthConstant.TOKEN_EXPIRE.getSeconds());
        return token;
    }

    /**
     * 根据令牌取出当前登录用户，存在则顺带续期。
     */
    public SysUser getUserByToken(String token) {
        if (!StringUtils.hasText(token)) {
            return null;
        }
        String key = AuthConstant.TOKEN_KEY_PREFIX + token;
        Object value = redisUtils.get(key);
        if (value == null) {
            return null;
        }
        redisUtils.expire(key, AuthConstant.TOKEN_EXPIRE.getSeconds());
        return (SysUser) value;
    }

    /**
     * 退出登录：删除令牌。
     */
    public void logout(String token) {
        if (StringUtils.hasText(token)) {
            redisUtils.delete(AuthConstant.TOKEN_KEY_PREFIX + token);
        }
    }
}
