package com.zb.byb.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BindInfo {

    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("身份证号")
    private String identity;
    @ApiModelProperty("手机号码")
    private String telNum;
    @ApiModelProperty("验证码")
    private String invitationCode;

}
