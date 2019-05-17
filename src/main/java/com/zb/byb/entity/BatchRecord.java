package com.zb.byb.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("批次记录")
public class BatchRecord{
    @ApiModelProperty("单据id")
    private String rcordId;
    @ApiModelProperty("批次id")
    private String batchid;
    @ApiModelProperty("批次号")
    private String batchName;
    @ApiModelProperty("品种")
    private String varietiesName;
    @ApiModelProperty("投苗数")
    private String tmts;
    @ApiModelProperty("投苗时间")
    private Date bizdate;
    @ApiModelProperty("日龄")
    private Integer curday;
    @ApiModelProperty("管理员名称")
    private String username;
    @ApiModelProperty("在养头数")
    private Integer curcnt;
    @ApiModelProperty("死亡头数")
    private Integer diecnt;
    @ApiModelProperty("当前页码")
    public int pageNumber = 1;
    @ApiModelProperty("每页大小")
    public int pageSize = 5;






}
