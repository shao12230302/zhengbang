package com.zb.byb.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Data
@ApiModel("设备领用申请")
public class EquipmentApply {
    @ApiModelProperty("记录id")
    private String rcordId;
    @ApiModelProperty("number")
    private String number;
    @ApiModelProperty("状态int")
    private int billStatusIndex;
    @ApiModelProperty("状态")
    private String billStatus;
    @ApiModelProperty("申请日期")
    private String bizDate;
    @ApiModelProperty("serviceId")
    private String serviceId;
    @ApiModelProperty("serviceName")
    private String serviceName;
    @ApiModelProperty("养户id")
    private String custId;
    @ApiModelProperty("金额")
    private Double equipAmt;
    @ApiModelProperty("养户姓名")
    private String custName;
    @ApiModelProperty("累计已领取设备金额")
    private Double totalPayment;
    //@ApiModelProperty("设备申请明细")
    //private List<Equipment> applyList;
    @ApiModelProperty("设备实际领用明细")
    private List<Equipment>  actEntrys;
    @ApiModelProperty("设备领用明细")
    private List<Equipment> entrys;
    @ApiModelProperty("是否委托他人领取")
    private Boolean isEntrust;
    @ApiModelProperty("被委托领取人姓名")
    private String entrustorName;
    @ApiModelProperty("被委托领取人id")
    private String entrustorId;
    @ApiModelProperty("被委托领取人身份证号")
    private String entrustorIdCard;
    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("开始时间")
    private String starttime;
    @ApiModelProperty("结束时间")
    private String endtime;
    private String state;
    public int pageNumber = 1;
    public int pageSize = 10;
    @ApiModelProperty("签名图片")
    private List<FileEntry> signerList;
    @ApiModelProperty("签名图片路径")
    private List<ActFileEntry> signerUrl;
    private Boolean isSigner ;//是否已签名
    //
    //private  String dsStatus;//状态

}
