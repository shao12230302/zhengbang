package com.zb.byb.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("结算明细")
public class BalanceDetail {

    @ApiModelProperty("猪苗金额")
    private Double pigamount;//
    private Double feedsettamount;//饲料金额
    private Double veterdurgsettamt;//兽药金额
    private Double recycleamount;//回收金额
    private Double lrbamount;//扣罚金额
    private Double subsidies;//补贴金额
    private Double settamount;//结算金额
    private Double payment;//补缴保证金
    private Double payforcustamount;//扣代付款
    private Double payableamount;//应付金额

}
