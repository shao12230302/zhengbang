package com.zb.byb.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: shaoys
 * @Mail: 415992946@qq.com
 * @Date: Created in 10:02 2019/5/5
 **/
@Data
public class SaleNotice {
    @ApiModelProperty("记录id")
    private String rcordId;
    @ApiModelProperty("养户id")
    private String custId;
    @ApiModelProperty("日期")
    private String bizDate;
    @ApiModelProperty("销售日期")
    private String bizYMD;
    @ApiModelProperty("销售时分秒")
    private String bizHIS;
    @ApiModelProperty("对接员")
    private String name;
    @ApiModelProperty("数量")
    private int qty;
    @ApiModelProperty("客户")
    private String customer;
    @ApiModelProperty("标题")
    private String number;

    private int pageNumber = 1;
    private int pageSize = 1000;
}
