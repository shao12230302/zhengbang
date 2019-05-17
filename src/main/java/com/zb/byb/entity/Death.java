package com.zb.byb.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ApiModel("死亡申报")
public class Death {
    @ApiModelProperty(value = "开始时间")
    private String starttime;
    @ApiModelProperty(value = "结束时间")
    private String endtime;
    @ApiModelProperty(value = "状态")
    private String billStatus;
    @ApiModelProperty(value = "状态id")
    private String billStatusIndex;
    @ApiModelProperty("养户id")
    private String custid;
    @ApiModelProperty("养户编号")
    private String pigfarmerCode;
    @ApiModelProperty("死亡申报id")
    private String rcordId;
    @ApiModelProperty("死亡申报批id")
    private String batchId;
    @ApiModelProperty("死亡申报批次号")
    private String batchNo;
    @ApiModelProperty("死亡日期")
    private String dieDate;
    @ApiModelProperty("死亡日龄")
    private Integer dieDay;
    @ApiModelProperty("喂养天数")
    private Integer freedDay;
    @ApiModelProperty("申报死亡头数")//
    private Integer applyDieCnt;
    @ApiModelProperty("死亡均重")
    private Double dieAvg;
    @ApiModelProperty("实际死亡头数")
    private Integer dieCnt;
    @ApiModelProperty("经度")
    private Double longitude;
    @ApiModelProperty("维度")
    private Double latitude;
    @ApiModelProperty("死猪照片")
    private List<ActFileEntry> imgUrl;
    @ApiModelProperty("死猪照片serverIds")
    private List<String> serverIds;
    @ApiModelProperty("申请记录状态")
    private String state;
    @ApiModelProperty("当前页码")
    public Integer pageNumber = 1;
    @ApiModelProperty("每页大小")
    public Integer pageSize = 10;
    @ApiModelProperty("单据编号")
    private String number;
    @ApiModelProperty("签名图片")
    private List<FileEntry> signerList;
    @ApiModelProperty("签名图片路径")
    private List<ActFileEntry> signerUrl;
    @ApiModelProperty("是否签名")
    private Boolean isSigner ;
}
