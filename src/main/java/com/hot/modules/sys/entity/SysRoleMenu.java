package com.hot.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hot.common.entity.BaseEntity;
import lombok.Data;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SysRoleMenu extends BaseEntity {

    private Integer menuId;
    private Integer parentId;
    private String menu;
    private List<Map<String,Object>> list;
    private List<SysRoleMenu> children;

}
