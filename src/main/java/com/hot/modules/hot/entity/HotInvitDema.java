package com.hot.modules.hot.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hot.common.entity.BaseEntity;
import lombok.Data;

/**
 * 合作需求表
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class HotInvitDema extends BaseEntity {

    private Integer id;
    private Integer invitId;
    private String productId;       // 产品id
    private String productName;     // 产品名称
    private String productModel;    // 产品型号
    private String productColor;    // 产品颜色
    private String coopChannel;     // 合作渠道
    private String releAcc;         // 发布账号
    private Integer releNum;        // 发布数量
    private String videoType;       // 视频类型
    private String videoAuth;       // 视频授权
    private String authPupo;        // 授权用途
    private String adAuth;          // 广告授权
    private String coopForm;        // 合作形式
    private String commRate;        // 佣金比例
    private String coopFee;         // 合作的费用
    private String platForm;        // 产品销售平台

}
