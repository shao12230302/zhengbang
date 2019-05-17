package com.zb.byb.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@ApiModel("我的信息")
public class MyInfo {
    //@JsonProperty("id")
    @ApiModelProperty("养户id")
    private String id;
    @ApiModelProperty("养户规模")
    private String cfwinternum;
    //@JsonProperty("fname")
    @ApiModelProperty("姓名")
    private String fname;
    //@JsonProperty("ftelno")
    @ApiModelProperty("手机号")
    private String ftelno;
    //@JsonProperty(value = "cfpigpen")
    @ApiModelProperty("猪舍地址")
    private String cfpigpen;
    //@JsonProperty(value = "fkhsj")
    @ApiModelProperty("开户时间")
    private String fkhsj;
    //@JsonProperty(value = "servicedep")
    @ApiModelProperty("所属服务部")
    private String servicedep;
    //@JsonProperty(value = "manager")
    @ApiModelProperty("管理员")
    private String manager;
    //@JsonProperty(value = "fcell")
    @ApiModelProperty("管理员电话")
    private String fcell;
    //@JsonProperty(value = "cfraisestate")
    @ApiModelProperty("饲养状态")
    private String cfraisestateText;
    //@JsonProperty("cflevel")
    @ApiModelProperty("我的星级")
    private String cflevel;
    //@JsonProperty("entrustedName")
    @ApiModelProperty("被委托人姓名")
    private String entrustName;
    //@JsonProperty("entrustedIdentity")
    @ApiModelProperty("被委托人身份证号")
    private String entrustIdcard;
    @ApiModelProperty("委托人电话")
    private String entrustPhone;

   /*@ApiModelProperty("被委托信息")
   private Entruster entruster;*/



}
