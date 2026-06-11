package com.hot.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hot.common.entity.BaseEntity;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SysButt extends BaseEntity {

    private Integer id;
    private Integer menuId;
    private String title;
    private String sign;
    private String icon;
    private Integer sort;

}
