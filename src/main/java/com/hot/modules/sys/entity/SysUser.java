package com.hot.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hot.common.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SysUser extends BaseEntity {

    private String id;
    private String token;
    private String name;
    private String loginname;
    private String password;
    private String percode;
    private String depname;
    private String loginip;     //登录IP
    private String logindate;   //登录时间
    private Integer loginflag;  //登录状态
    private Date passwTime;     //密码修改时间
    private Date errorTime;     //密码错误时间
    private Integer errorCount; //密码错误次数
    private String remarks;     //备注
    private Integer userType;
    private String userTypes;

    private int menurole;
    private int modurole;
    private int buttrole;
    private int datarole;

    private List<Integer> buttlist;
    private List<Integer> datalist;
    private List<Integer> menulist;
    private List<Integer> modulist;

}
