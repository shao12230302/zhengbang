package com.zb.byb.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 作者：谢李
 * {"id":"vKYTT1wJTV+A7XdlVys=","idcard":"测试idcard",
 * "isDefault":true,"name":"测试","phone":"测试phone"}
 */
@Data
public class EntrustInfo {

    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("身份证")
    private String  idcard;
    @ApiModelProperty("是否默认")
    private String  isDefault;
    @ApiModelProperty("名字")
    private String  name;
    @ApiModelProperty("电话")
    private String  phone;
    @ApiModelProperty("养户id")
    private String custId;

    public int pageNumber = 1;
    public int pageSize = 100;
}
