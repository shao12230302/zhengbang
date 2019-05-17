package com.zb.byb.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@ApiModel("投苗申请")
public class Farmer implements Serializable {
    @ApiModelProperty("养户姓名")
    private String name;
    @ApiModelProperty("养户规模")
    private String size;
}
