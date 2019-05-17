package com.zb.byb.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@ApiModel("投苗申请")
public class TouMiao {
    @ApiModelProperty("记录ID")
    private String rcordId;
    @ApiModelProperty("养户id")
    private String custId;
    @ApiModelProperty("养户名称")
    private String custName;
    @ApiModelProperty("规模")
    private String scope;
    @ApiModelProperty("申请投苗日期")
    private String applyDate;
    @ApiModelProperty("筛选开始时间")
    private String starttime;
    @ApiModelProperty("筛选结束时间")
    private String endtime;

    @ApiModelProperty("身份证")
    private  String  identityCards;
    @ApiModelProperty("保证金余额")
    private String balance;
    @ApiModelProperty("投苗数量")
    private int num;
    @ApiModelProperty("赁栏舍名称")
    private String villageName;
    @ApiModelProperty("赁栏舍villId")
    private String villId;
    @ApiModelProperty("养户单头承担租赁费")
    private double oneHandRent;
    @ApiModelProperty("billStatus")
    private String billStatus = "";
    @ApiModelProperty("当前投苗状态")
    private int billStatusIndex = STATUS_KEEP;//1：表示待审核（10保存，20提交）  2：表示已审核（30审核）
    @ApiModelProperty("状态显示1、2、3")
    private String state;
    @ApiModelProperty("备注")
    private String remark = "";
    @ApiModelProperty("sessionId")
    private String sessionId;
    @ApiModelProperty("能否签名")
    private Integer canSigner;

    @ApiModelProperty("实际投苗日期")
    private String actDate;
    @ApiModelProperty("实际投苗数量")
    private Integer actNum;
    @ApiModelProperty("实际投苗均重")
    private Double actAvgWg;
    @ApiModelProperty("清洗消毒工作是否完成")
    private Boolean disinfectFinished;
    @ApiModelProperty("保温箱、屋中屋是否准备")
    private Boolean incubatorReadied;
    @ApiModelProperty("保温灯是否准备")
    private Boolean heatLampReadied;
    @ApiModelProperty("发电机是否正常使用")
    private Boolean electricGeneratorReadied;
    @ApiModelProperty("圆盘料槽是否准备")
    private Boolean troughReadied;
    @ApiModelProperty("物资准备")
    private Boolean materialsReadied;
    @ApiModelProperty("是否准备加药桶")
    private Boolean barrelReadied;
    @ApiModelProperty("上猪台是否准备")
    private Boolean pigTableReadied;
    @ApiModelProperty("加温/降温设施是否达到标准")
    private Boolean temperatureControlReadied;

    @ApiModelProperty("图片")
    private List<FileEntry> pigpenInside;
    @ApiModelProperty("签名图片")
    private List<FileEntry> signerList;
    @ApiModelProperty("签名图片路径")
    private List<ActFileEntry> signerUrl;
    private Boolean isSigner ;//是否已签名
    //待审核
    public final static int STATUS_KEEP = 1;
    //表示已审核（30审核）
    public final static  int STATUS_APPROVE = 2;

    public int pageNumber = 1;
    public int pageSize = 1000;
    @ApiModelProperty("单据编号")
    private String number;//
}
