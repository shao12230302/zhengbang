package com.zb.byb.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("文件实体")
public class FileEntry {

    private String imgContent;//图片字符串

    private String imgType;//文件类型
}
