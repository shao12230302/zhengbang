package com.zb.byb.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@ApiModel("交易明细")
public class DealDetail {

   private String custId;//养户id
   private String cust;//养户姓名
   private String number;//单据编号
   private String status;//单据状态
    @ApiModelProperty("业务日期")//
    private String bizDate;
    private String paymentDate;//缴款日期
    private String payeeId;//收款人id
    private String payee;//收款人姓名
    @ApiModelProperty("交易类型")
    private String transactiontype;
    @ApiModelProperty("交易金额")
    private Double money;
    @ApiModelProperty("余额")
    private Double yuE;//需要新增
    @ApiModelProperty("当前页码")
    public int pageNumber = 1;
    @ApiModelProperty("每页大小")
    public int pageSize = 10;

}
