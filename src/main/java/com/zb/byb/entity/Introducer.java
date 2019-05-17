package com.zb.byb.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("介绍人信息")
public class Introducer {
    @ApiModelProperty("身份证号")
    private String idno;
    @ApiModelProperty("介绍人姓名")
    private String name;
    @ApiModelProperty("介绍人编号")
    private String number;

    private String rcordId;
    @ApiModelProperty("服务部名称")
    private String serviceName;
    @ApiModelProperty("服务部id")
    private String servicedeptid;
    public int pageNumber = 1;
    public int pageSize = 10;


}
