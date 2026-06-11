package com.hot.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hot.common.entity.BaseEntity;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class BasicTree extends BaseEntity {

    private Integer id;
    private Integer parentId;
    private String parentIds;
    private String code;
    private String name;
    private List<BasicTree> children;

}
