package com.zb.byb.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@ApiModel("设备信息")
public class Equipment implements Serializable {
    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("设备名称id")
    private String materialId;
    @ApiModelProperty("设备名称")
    private String materialName;
    @ApiModelProperty("设备数量")
    private String qty;
    @ApiModelProperty("计量单位")
    private String unit;
    @ApiModelProperty("规格")
    private String model;
    @ApiModelProperty("单价")
    private Double price;
    @ApiModelProperty("金额")
    private Double amount;
    @ApiModelProperty("库存")
    private String stock;//库存

}
