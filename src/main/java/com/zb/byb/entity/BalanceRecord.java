package com.zb.byb.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@ApiModel("结算记录")
public class BalanceRecord {
    @ApiModelProperty("结算申请id")
    private String rcordId;
    @ApiModelProperty("结算批次id")
    private String batchId;
    @ApiModelProperty("结算批次名称")
    private String batchName;
    @ApiModelProperty("结算日期")
    private String bizdate;
    @ApiModelProperty("投苗均重")
    private Double avgWeight;
    @ApiModelProperty("上市均重")
    private Double listedverWeight;
    @ApiModelProperty("成活率")
    private Double aliveRate;//缺少
    @ApiModelProperty("料肉比")
    private Double meatrate;
    @ApiModelProperty("正品率")
    private Double realrate;
    @ApiModelProperty("结算明细")
    private BalanceDetail balanceDetail;
    @ApiModelProperty("状态")
    private String state;
    //签名
    private List<FileEntry> fileEntry;
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
    @ApiModelProperty("签名图片")
    private List<FileEntry> signerList;
    @ApiModelProperty("是否已签名")
    private Boolean isSigner;

    @ApiModelProperty("签名图片路径")
    private List<ActFileEntry> signerUrl;


}
