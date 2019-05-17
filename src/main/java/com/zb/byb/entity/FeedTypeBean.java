package com.zb.byb.entity;

import lombok.Data;
import java.io.Serializable;
/**
 * Created by zhengbang on 2017/6/12.
 */
@Data
public class FeedTypeBean implements Serializable {

    public String consumeQty;//耗料包数
    public String feedName;//饲料名称
    public String feedId;//饲料id
    public String  avgConsumeQty;//头均耗料
    public String   sumConsumeQty;//总耗料KG
    public String   columnQty;//存栏数
    public String  alias;//物料品种名称
    public String  batchName;//批次号
    public String   dayOld;//日龄
    public String   batchId;//批次id
    public String   feedVarietiesID;//饲喂品种id
    public String sumReceve;//已领用数量
    public String quotarecevenum;//定额领用数量
    public String dayGion;//标准日喂料量
    public String isShow;//是否显示标准日喂料量
    public String bclybs;//本次领用包数
    public String curcnt;
    public String curday;

}
