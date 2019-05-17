package com.zb.byb.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 作者：谢李
 */
@Data
public class QuestionReportInfo implements Serializable {
    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("记录id")
    private String rcordId;
    @ApiModelProperty("反馈日期")
    private String bizdate;
    @ApiModelProperty("养户id")
    private String custId;
    @ApiModelProperty("养户名称")
    private String custname;
    @ApiModelProperty("回复id")
    private String replypersonid;
    @ApiModelProperty("回复名称")
    private String replypersonname;
    @ApiModelProperty("问题")
    private String details;
    @ApiModelProperty("问题答复")
    private String replydetails;
    @ApiModelProperty("答复时期")
    private String replydate;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("排序")
    private String order;
    @ApiModelProperty("扩展字段")
    private String extendsOne;

    public int pageNumber = 1;
    public int pageSize = 1000;
}
