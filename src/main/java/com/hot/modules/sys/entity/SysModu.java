package com.hot.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hot.common.entity.BaseEntity;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SysModu extends BaseEntity {

    private Integer id;
    private Integer menuId;
    private String menuName;
    private String title;
    private String path;
    private String component;
    private String sign;
    private String icon;
    private Integer sort;

}
