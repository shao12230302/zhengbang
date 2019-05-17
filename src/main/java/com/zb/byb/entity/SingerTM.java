package com.zb.byb.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: shaoys
 * @Mail: 415992946@qq.com
 * @Date: Created in 0:26 2019/5/12
 **/
@Data
public class SingerTM {
    @ApiModelProperty("记录ID")
    private String rcordId;
    @ApiModelProperty("文件")
    private FileEntry fileEntry;

}
