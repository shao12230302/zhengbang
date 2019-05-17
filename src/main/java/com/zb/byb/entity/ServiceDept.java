package com.zb.byb.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("服务部")
public class ServiceDept {
    @ApiModelProperty("服务部id")
    private String rcordId;
    @ApiModelProperty("服务部名字")
    private String name;
    @ApiModelProperty("当前页码")
    public Integer pageNumber = 1;
    @ApiModelProperty("每页大小")
    public Integer pageSize = 10;
}
