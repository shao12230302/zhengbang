package com.zb.byb.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 作者：谢李
 * "UnitPrice":12.1,"isDrug":"1","materialName":"猪瘟活疫苗（传代细胞苗）（20头份）(齐鲁动物保健品有限公司（生药）)",
 * "materialid":"Va4AAAAI+8FECefw","measureUnit":"瓶","stanard":"20头份/瓶",
 * "stock":1339,"supplierId":"Va4AAAAAPMg3xn38"
 */
@Data
public class MaterialInfo {

    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("价格")
    private Double  UnitPrice;
    @ApiModelProperty("isDrug")
    private String  isDrug;
    @ApiModelProperty("药名")
    private String  materialName;
    @ApiModelProperty("药品id")
    private String  materialid;
    @ApiModelProperty("药品单位")
    private String  measureUnit;
    @ApiModelProperty("标准")
    private String  stanard;
    @ApiModelProperty("stock")
    private String  stock;
    @ApiModelProperty("供应商id")
    private String  supplierId;
    @ApiModelProperty("养户id")
    private String custId;
    @ApiModelProperty("模糊查询药品")
    private String fuzzyKey;
    @ApiModelProperty("批次id")
    private String batchId;

    private String number;

    public int pageNumber = 1;
    public int pageSize = 10;
}
