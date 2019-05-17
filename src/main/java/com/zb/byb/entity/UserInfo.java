package com.zb.byb.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@ApiModel("注册开户")
public class UserInfo {
    @ApiModelProperty("申请条件")
    private String requirements;
    @ApiModelProperty("姓名")
    private String custName;
    private String sex;
    @ApiModelProperty("猪舍地址")
    private List pigpenList;
    @ApiModelProperty("身份证号")
    private String idno;
    @ApiModelProperty("手机号码")
    private String telNum;
    @ApiModelProperty("介绍人id")
    private String introducerId;
    @ApiModelProperty("介绍人")
    private String introducer;
    @ApiModelProperty("介绍人所在服务部")
    private String introducerDept;
    @ApiModelProperty("邀请码/验证码")
    private String invitationCode;
    @ApiModelProperty("服务部")
    private String serviceName;
    @ApiModelProperty("服务部id")
    private String serviceId;
    public int pageNumber = 1;
    public int pageSize = 10;

}
