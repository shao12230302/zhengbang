package com.zb.byb.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("委托人信息")
public class Entruster {
    @ApiModelProperty("委托人姓名")
    private String entrustName;
    @ApiModelProperty("委托人身份证")
    private String entrustIdcard;
    @ApiModelProperty("委托人电话")
    private String entrustPhone;

}
