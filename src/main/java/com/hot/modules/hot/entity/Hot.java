package com.hot.modules.hot.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hot.common.entity.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * 红人
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Hot extends BaseEntity {

    private Integer id;
    private String name;        // 姓名
    private String archName;    // 档案名
    private Integer state;      // 状态码
    private String states;      // 状态
    private String nickname;    // 昵称
    private Long avgPlayCount;  // 平均播放数
    private String scoreLevel;  // 评分等级

    private String country;     // 国家
    private String hotLevel;    // 红人量级
    private String hotType;     // 红人类型
    private String channel;     // 渠道
    private String hotsId;      // 红人ID
    private String productName; // 合作产品
    private String coopFee;     // 合作费用
    private Integer isMatch;    // 是否匹配
    private String isMatchs;    // 是否匹配

    private HotBasic basicyt;
    private HotBasic basicig;
    private HotBasic basictk;

}
