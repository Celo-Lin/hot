package com.hot.modules.hot.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hot.common.entity.BaseEntity;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class HotCoopSett extends BaseEntity {

    private Integer id;
    private Integer coopId;
    private String applyTime;   // 申请时间
    private String payTime;     // 付款时间
    private String payAmount;   // 付款金额

}
