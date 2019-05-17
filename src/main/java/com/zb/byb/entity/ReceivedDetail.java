package com.zb.byb.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("领用详情")
public class ReceivedDetail {
    @ApiModelProperty("金额")
    private String num;
    @ApiModelProperty("理由")
    private String reason;
    @ApiModelProperty("时间")
    private String time;
    @ApiModelProperty("重量")
    private String weight;


}
