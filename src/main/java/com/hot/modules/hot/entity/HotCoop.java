package com.hot.modules.hot.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hot.common.entity.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * 合作单
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class HotCoop extends BaseEntity {

    private Integer id;
    private String docNum;      // 单据号
    private String bdName;      // BD名称
    private String connTime;    // 建联时间
    private String agccTime;    // 同意沟通合作时间
    private Integer hotbId;     // 红人详情id
    private String hotsId;      // 红人id
    private String channel;     // 渠道
    private String country;     // 国家
    private Integer invitId;    // 合作邀约单id
    private String invitDocNum; // 合作邀约单号
    private Integer projectId;  // 项目Id
    private String projectName; // 项目名称
    private Integer state;      // 状态码
    private String states;      // 状态

    private List<HotCoopProduct> prodList;
    private List<HotCoopSett> settList;
    private List<HotCoopData> dataList;

}
