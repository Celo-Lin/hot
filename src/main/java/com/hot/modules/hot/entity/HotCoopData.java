package com.hot.modules.hot.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hot.common.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 内容数据表
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class HotCoopData extends BaseEntity {

    private Integer id;
    private Integer coopId;          // 合作单id
    private String releChan;         // 发布渠道
    private String releAcct;         // 发布账号
    private BigDecimal amount;       // 付款金额
    private String applyTime;        // 请款时间
    private String payTime;          // 完成付款时间
    private String relesDate;        // 需求的发布排期
    private String vidType;          // 视频类型（长/短）
    private String vidDraftUrl;      // 视频草稿链接
    private String mateName;         // 广告素材名称
    private String proDraftTime;     // 提供草稿时间
    private String releTime;         // 发布时间
    private String onlineUrl;        // 上线链接
    private String adCode;           // 广告授权码
    private String adCodeExpiry;     // 广告授权码有效期
    private String duration;         // 监控时长
    private String creativ;          // 内容创意度
    private String score;            // 评分
    private String scoreLevel;       // 评分等级
    private Long views;              // 观看量
    private Long likes;              // 点赞数
    private Long comments;           // 评论数
    private Long shares;             // 分享数
    private Long favors;             // 收藏数
    private String rateNox;          // 互动率 Nox
    private Long dljClicks;          // 短链点击数
    private String cpm;              // CPM 千次展示成本
    private String cpc;              // CPC 单次点击成本
    private String period;           // 投放期
    private String spend;            // 消耗金额
    private Long plays;              // 播放量
    private Long clicks;             // 点击量
    private Long conve;              // 转化量
    private String roas;             // 广告 ROAS

}
