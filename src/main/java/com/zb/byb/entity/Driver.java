package com.zb.byb.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("拉料司机")
public class Driver {
    /*@ApiModelProperty("拉料司机姓名")
    private String driverName;
    @ApiModelProperty("拉料司机姓id")
    private String driverId;
    @ApiModelProperty("拉料司机身份证号")
    private String driverIdcard;
    @ApiModelProperty("拉料司机车牌号")//车牌号
    private String driverVehicleno;*/

    private String id;//司机id
    private String isDefault;//是否默认
    private String name;//名字
    private String number;//身份证号
    private String phone;//号码
    private String vehicleno;//车牌号
}
