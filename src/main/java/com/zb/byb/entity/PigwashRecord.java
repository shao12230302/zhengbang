package com.zb.byb.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@ApiModel("饲料领用记录")
public class PigwashRecord {
    @ApiModelProperty("领用日期")
    private Date receiveDate;
    @ApiModelProperty("饲料品种")
    private String bread;
    @ApiModelProperty("规格")
    private String spec;
    @ApiModelProperty("数量")
    private Integer amount;
    @ApiModelProperty("单价")
    private Double price;
    @ApiModelProperty("金额")
    private Double payment;
    @ApiModelProperty("当前页码")
    public int pageNumber = 1;
    @ApiModelProperty("每页大小")
    public int pageSize = 10;
    @ApiModelProperty("单据编号")
    private String number;//
}
