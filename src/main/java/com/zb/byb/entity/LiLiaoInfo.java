package com.zb.byb.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("领料退料信息")
public class LiLiaoInfo {
    @ApiModelProperty("饲料id")
    private String feedId;
    @ApiModelProperty("饲料品种")
    private String feedName;
    @ApiModelProperty("数量（包数）")
    private Integer bclybs;
    @ApiModelProperty("单价")
    private Double price;
    @ApiModelProperty("金额")
    private Double amount;
    @ApiModelProperty("已领")
    public String sumReceve;//已领用数量
    @ApiModelProperty("定额")
    public String quotarecevenum;//定额领用数量
    public String isShow;
}
