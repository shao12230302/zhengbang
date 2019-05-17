package com.zb.byb.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("领用记录")
public class ReceivedRecord {

    private String type;
    private String batchName;
    private String batchId;
    @ApiModelProperty("明细")
    private List<ReceivedDetail> details;
    /*@ApiModelProperty("设备")
    private List<ReceivedDetail>equipment;
    @ApiModelProperty("饲料")
    private List<ReceivedDetail> feed;
    @ApiModelProperty("种猪")
    private List<ReceivedDetail> pig;*/

    @ApiModelProperty(value = "开始时间")
    private String starttime;
    @ApiModelProperty(value = "结束时间")
    private String endtime;
    @ApiModelProperty(value = "单据状态")//保存
    private String billStatus;
    @ApiModelProperty(value = "状态id")//10
    private String billStatusIndex;
    @ApiModelProperty("当前页码")
    public Integer pageNumber = 1;
    @ApiModelProperty("每页大小")
    public Integer pageSize = 10;
    @ApiModelProperty("单据编号")
    private String number;//
    private String state;//

}
