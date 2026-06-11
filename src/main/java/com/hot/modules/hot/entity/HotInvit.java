package com.hot.modules.hot.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hot.common.entity.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * 合作邀约单
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class HotInvit extends BaseEntity {

    private Integer id;
    private String docNum;      // 单据号
    private String bdName;      // BD名称
    private String connTime;    // 建联时间
    private String agccTime;    // 同意沟通合作时间
    private Integer hotbId;     // 红人详情id
    private String hotsId;      // 红人id
    private String channel;     // 渠道
    private String country;     // 国家
    private Integer state;      // 状态码
    private String states;      // 状态

    private List<HotInvit> hotsList;
    private List<HotInvitDema> demaList;


}
