package com.zb.byb.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 作者：谢李
 */
@Data
public class PigEntry {
    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("时间")
    private String pigingDate;
    @ApiModelProperty("品种id")
    private String materialId;
    @ApiModelProperty("品种")
    private String materialName;
    @ApiModelProperty("数量")
    private String qty;
    @ApiModelProperty("日龄")
    private String dayAge;
    @ApiModelProperty("平均重")
    private String avgWeight;
    @ApiModelProperty("标准重")
    private String baseWeight;
    @ApiModelProperty("重")
    private String weight;
    @ApiModelProperty("标准单价")
    private String basePrice;
    @ApiModelProperty("超重单价")
    private String surpPrice;
    @ApiModelProperty("低重单价")
    private String insPrice;
    @ApiModelProperty("金额")
    private String ammount;
}
