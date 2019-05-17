package com.zb.byb.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@ApiModel("理财申请")
public class Financing {
    @ApiModelProperty("养户")
    private Farmer farmer;
    @ApiModelProperty("申请日期")
    private Date applyDate;
    @ApiModelProperty("申请类型")
    private String applyType;
    @ApiModelProperty("可用结算款金额")
    private Double avaibleBalance;
    @ApiModelProperty("可赎回金额")
    private Double backBalance;
    @ApiModelProperty("本次可申购/赎回金额")
    private Double chooseBalance;
    @ApiModelProperty("购买需知")
    private String buyKnow;
    @ApiModelProperty("单据编号")
    private String number;//




}
