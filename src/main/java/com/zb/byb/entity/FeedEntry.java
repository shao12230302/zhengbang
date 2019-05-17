package com.zb.byb.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 作者：谢李
 */
@Data
public class FeedEntry {
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("时间")
    private String applyDate;
    @ApiModelProperty("数量")
    private String qty;
    @ApiModelProperty("单价")
    private String price;
    @ApiModelProperty("标准数量")
    private String baseQty;
    @ApiModelProperty("金额")
    private String amount;
    @ApiModelProperty("是否自己领取")
    private String isSelf;
    @ApiModelProperty("司机id")
    private String driverId;
    @ApiModelProperty("司机名")
    private String driverName;
    @ApiModelProperty("车牌")
    private String driverCarNo;
    @ApiModelProperty("品名id")
    private String materialId;
    @ApiModelProperty("规格")
    private String model;
    @ApiModelProperty("品名")
    private String materialName;

}
