package com.hot.modules.hot.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hot.common.entity.BaseEntity;
import lombok.Data;

/**
 * 项目
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Project extends BaseEntity {

    private Integer id;
    private String name;

}
