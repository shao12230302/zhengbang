package com.zb.byb.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 作者：谢李
 */
@Data
@ApiModel("常见问题")
public class NormalQuestionInfo implements Serializable
{
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("值")
    private String value;
}
