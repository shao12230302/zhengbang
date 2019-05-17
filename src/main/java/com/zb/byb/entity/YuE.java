package com.zb.byb.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel("余额")
public class YuE {
    @ApiModelProperty("当前余额")
    private Double paymentAmt;
    @ApiModelProperty("交易明细")
    private List<DealDetail> security;
    @ApiModelProperty("当前页码")
    private Integer pageNumber = 1;
    @ApiModelProperty("每页大小")
    private Integer pageSize = 10;
    @ApiModelProperty(value = "开始时间")
    private String starttime;
    @ApiModelProperty(value = "结束时间")
    private String endtime;

}
