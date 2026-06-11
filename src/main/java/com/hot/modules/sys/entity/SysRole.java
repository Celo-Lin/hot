package com.hot.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hot.common.entity.BaseEntity;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SysRole extends BaseEntity {

    private Integer id;
    private String name;
    private List<Integer> list;
    private List<Integer> buttlist;
    private List<Integer> datalist;
    private List<Integer> menulist;
    private List<Integer> modulist;

}
