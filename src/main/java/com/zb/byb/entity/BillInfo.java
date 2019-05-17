package com.zb.byb.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 对账单实体类
 * 作者：谢李
 */
@ApiModel("对账单实体类")
@Data
public class BillInfo {

    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("记录id")
    private String rcordId;
    @ApiModelProperty("养户id")
    private String custId;
    @ApiModelProperty("养户名称")
    private String custName;
    @ApiModelProperty("批次")
    private String batchId;
    private String batchName;
    @ApiModelProperty("账单日期")
    private String bizdate;
    @ApiModelProperty("序号")
    private String number;
    @ApiModelProperty("状态码")
    private String billStatusIndex;
    @ApiModelProperty("状态")
    private String billStatus;
    @ApiModelProperty("endSurplus")
    private String endSurplus;
    @ApiModelProperty("期间")
    private String period;
    @ApiModelProperty("开始时间")
    private String starttime;
    @ApiModelProperty("结束时间")
    private String endtime;
    @ApiModelProperty ("部门")
    private String department;
    @ApiModelProperty ("扩展字段")
    private String extendsOne;

    private List<PigEntry> pigEntry = new ArrayList<>();
    private List<FeedEntry> feedEntry = new ArrayList<>();
    private List<DrugEntry> drugEntry = new ArrayList<>();
    private List<EquipEntry> equipEntry = new ArrayList<>();


    public int pageNumber = 1;
    public int pageSize = 100;

}
