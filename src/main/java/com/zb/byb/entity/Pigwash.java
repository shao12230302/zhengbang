package com.zb.byb.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@ApiModel("饲料信息")
public class Pigwash implements Serializable {
    private String batchId;//养猪批次id
    private String batchName;//养猪批次号
    @ApiModelProperty("饲料id")
    private String feedId;
    @ApiModelProperty("饲料品种")
    private String feedName;
    @ApiModelProperty("数量（包数）")
    private Integer consumeQty;
    @ApiModelProperty("单价")
    private Double price;
    @ApiModelProperty("金额")
    private Double amount;
    /*@ApiModelProperty("已领")
    private Double received;
    @ApiModelProperty("定额")
    private Double quota;*/
    @ApiModelProperty("已领")
   public String sumReceve;//已领用数量
    @ApiModelProperty("定额")
    public String quotarecevenum;//定额领用数量
}
