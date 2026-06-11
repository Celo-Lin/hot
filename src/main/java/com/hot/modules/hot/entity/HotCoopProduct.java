package com.hot.modules.hot.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hot.common.entity.BaseEntity;
import lombok.Data;

/**
 * 产品合作信息表
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class HotCoopProduct extends BaseEntity {

    private Integer id;
    private Integer coopId;          // 合作单id
    private Integer productId;       // 产品id
    private String productName;      // 产品名称
    private String productModel;     // 产品型号
    private String productColor;     // 产品颜色
    private String videoAuth;        // 视频授权
    private String videoAuthPur;     // 视频授权用途
    private String adAuth;           // 广告授权
    private String coopForm;         // 合作的形式
    private String commRate;         // 佣金比例
    private String payMethod;        // 付款方式
    private String platForm;         // 产品销售平台
    private String payInfo;          // 收款信息
    private String payAddres;        // 收款地址
    private String coopFee;          // 合作费用
    private String sashNo;           // 寄样单号
    private String carrier;          // 运输商
    private String sashTime;         // 寄样时间
    private String wlStatus;         // 物流状态
    private String dhDate;           // 到货日期
    private String utmUrl;           // UTM追踪链接
    private String salesPeriod;      // 销售期
    private Long clicks;             // 访问量/点击量
    private Long sales;              // 销售数量
    private String disCode;          // 折扣代码
    private String gmv;              // GMV
    private String roas;             // ROAS

}
