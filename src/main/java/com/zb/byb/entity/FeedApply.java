package com.zb.byb.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel("领料申请")
public class FeedApply {
    @ApiModelProperty(value = "开始时间")
    private String starttime;
    @ApiModelProperty(value = "结束时间")
    private String endtime;
    @ApiModelProperty(value = "状态")
    private String billStatus;
    @ApiModelProperty(value = "状态id")
    private String billStatusIndex;
    @ApiModelProperty("当前页码")
    public int pageNumber = 1;
    @ApiModelProperty("每页大小")
    public int pageSize = 5;
    @ApiModelProperty("领料申请id")
    private String rcordId;
    @ApiModelProperty("领料申请批次id")
    private String batchId;
    @ApiModelProperty("领料申请批次名")
    private String batchName;
    @ApiModelProperty("计划拉料日期")
    private String plandate;
    @ApiModelProperty("拉料司机姓名")
    private String driverName;
    @ApiModelProperty("拉料司机姓id")
    private String driverId;
    @ApiModelProperty("拉料司机身份证号")
    private String driverIdcard;
    @ApiModelProperty("拉料司机车牌号")//车牌号
    private String driverVehicleno;
    @ApiModelProperty("申请类别：0：领料，1：退料")
    private String type;
    @ApiModelProperty("申请领料明细")
    private List<LiLiaoInfo> pickDetail;
    @ApiModelProperty("实际领料明细")
    private List<LiLiaoInfo>  actEntrys;
    @ApiModelProperty("签名图片")
    private List<FileEntry> signerList;
    @ApiModelProperty("签名图片路径")
    private List<ActFileEntry> signerUrl;
    private Boolean isSigner ;//是否已签名
    @ApiModelProperty("任务状态")
    private String state;
    @ApiModelProperty("单据编号")
    private String number;//
    private  String dsStatus;//状态


}
