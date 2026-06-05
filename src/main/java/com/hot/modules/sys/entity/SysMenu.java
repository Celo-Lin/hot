package com.hot.modules.sys.entity;

import com.hot.common.entity.BaseEntity;
import lombok.Data;

import java.util.List;

@Data
public class SysMenu extends BaseEntity {

    private Integer id;
    private Integer parentId;
    private String parentIds;
    private String title;
    private String path;
    private String component;
    private String sign;
    private String icon;
    private Integer sort;
    private Integer type;
    private List<SysMenu> children;
    private List<SysModu> modu;
    private List<SysButt> butt;

}
