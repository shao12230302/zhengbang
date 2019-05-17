package com.zb.byb.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 记录
 * 作者：谢李
 */
@Data
@ApiModel("数据记录实体对象")
public class DataRecord implements Serializable
{
    @ApiModelProperty("id唯一标识")
    private String id;
    @ApiModelProperty("微信openId")
    private String openId;
    @ApiModelProperty("数据传递id")
    private String dataId;
    @ApiModelProperty("用户名称")
    private String userName;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("值")
    private String value;
    @ApiModelProperty("展示值")
    private String showValue;
    @ApiModelProperty("记录类型")
    private String recordType;
    @ApiModelProperty("记录序号")
    private String recordNum;
    @ApiModelProperty("记录状态")
    private String recordStatus;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("开始时间")
    private Date startTime;
    @ApiModelProperty("结束时间")
    private Date endTime;
    @ApiModelProperty("扩展字段")
    private String extendOnew;

}
