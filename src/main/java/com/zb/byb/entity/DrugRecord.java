package com.zb.byb.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@ApiModel("兽药领用记录")
public class DrugRecord {
    @ApiModelProperty("领用日期")
    private Date receiveDate;
    @ApiModelProperty("药名")
    private String name;
    @ApiModelProperty("规格")
    private String spec;
    @ApiModelProperty("数量")
    private Integer amount;
    @ApiModelProperty("单价")
    private Double price;
    @ApiModelProperty("金额")
    private Double payment;


}
