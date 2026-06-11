package com.hot.modules.hot.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hot.common.entity.BaseEntity;
import lombok.Data;

/**
 * 红人信息表（IG）
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class HotBasic extends BaseEntity {

    private Integer id;
    private Integer hotId;
    private String channel;     // 渠道
    private String hotsId;      // 红人id
    private String nickname;    // 昵称
    private String country;     // 国家
    private String contact;     // 联系方式
    private String hotLevel;    // 红人量级
    private String hotType;     // 红人类型
    private Long follower;      // 粉丝量
    private String juxingUrl;   // 聚星平台链接
    private String urlLink;     // URL链接
    private Long avgPlayCount;  // 平均播放量
    private String avgIactRate; // 平均互动率
    private Long longAvgViews;  // 最近10个长视频平均观看量
    private String longIactRate;// 最近10个长视频平均互动率
    private Long shortAvgViews; // 最近10个短视频平均观看量
    private String scoreLevel;  // 评分等级

}
