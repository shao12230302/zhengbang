package com.zb.byb.service.impl;

import com.zb.byb.common.C;
import com.zb.byb.common.Commonconst;
import com.zb.byb.entity.Balance;
import com.zb.byb.entity.BalanceDetail;
import com.zb.byb.entity.BalanceRecord;
import com.zb.byb.service.BalanceService;
import com.zb.byb.util.BackTransmitUtil;
import com.zb.byb.util.Image2Base64Util;
import com.zb.byb.util.JsonPluginsUtil;
import com.zb.byb.util.MethodName;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class BalanceServiceImpl implements BalanceService {
    @Autowired
    private BackTransmitUtil backTransmitUtil;
    @Override
    public Balance initInfoByBatchId(String batchId, String userId) throws Exception {
        if (C.checkNullOrEmpty(batchId) || C.checkNullOrEmpty(userId))
            throw  new Exception("未传入id!");
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("batchId",batchId);
        map.put("custId",userId);
        map.put("source",Commonconst.WX_Flag);//微信
        map.put("data",param);
        String data= JSONObject.fromObject(map).toString();
        String jsonStr = backTransmitUtil.invokeFunc(data,MethodName.METHOD_NAME_APPLY_SETTLEBILL );
        //{"code":"0000","data":{"avgKg":7.72,"batchId":"Va4AAAWIE6dSsdKc","batchName":"叶飞004","bizdate":"2018-10-11","bthcnt":1000,"cfsscps":0,"cfsszps":0,"chl":0,"cpjz":0,"ssrl":0,"zpjz":0},"msg":"获取成功!"}
        return JsonPluginsUtil.jsonToBean(jsonStr,Balance.class);
    }

    @Override
    public String balanceApply(Balance balance, String userId) throws Exception {
        if (C.checkNullOrEmpty(balance) || C.checkNullOrEmpty(userId))
            throw  new Exception("未传入数据!");
        Map<String, Object> map = new HashMap<>();
        map.put("source",Commonconst.WX_Flag);//微信
        map.put("custId",userId);
        map.put("data",balance);
        String data= JSONObject.fromObject(map).toString();
        String jsonStr = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_SAVE_SETTLEBILL);
        return JsonPluginsUtil.isRequestSuccessBackId(jsonStr);
    }

    @Override
    public List<BalanceRecord> getBalanceRecord(BalanceRecord balanceRecord, String userId) throws Exception {
        if (C.checkNullOrEmpty(balanceRecord) || C.checkNullOrEmpty(userId))
            throw  new Exception("未传入id!");
        Map<String, Object> map = new HashMap<>();
        map.put("source", Commonconst.WX_Flag);//微信
        map.put("custId",userId);
        map.put("data",balanceRecord);
        String data= JSONObject.fromObject(map).toString();
        String jsonStr = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_QUERY_SETTLEBILL);
        //{"code":"0000","count":28483,"data":[{"avgWeight":13.79,"batchId":"TsVQKmbGg4XgU5oBWApJqFKx0pw=","batchName":"曾广生020","billStatus":"已删除","billStatusIndex":"90","feedsettamount":450068,"listedverWeight":"115.52","material":"保育苗","materialId":"Va4AAAAKl5XqXG71","meatrate":"2.82","payment":8200,"pigamount":279384,"rcordId":"Va4AAAATxu4uefsF","realrate":94.87,"recycleamount":806360.000512,"settamount":57552.5726075492,"state":3,"subsidies":0,"veterdurgsettamt":17234.4779044508},{"avgWeight":10.06,"batchId":"Va4AAAH/EaRSsdKc","batchName":"秦友亮002","billStatus":"已删除","billStatusIndex":"90","feedsettamount":610964,"listedverWeight":"106.81","material":"断奶苗","materialId":"Va4AAAAKl5TqXG71","meatrate":"2.71","payableamount":60945,"payment":0,"pigamount":332040,"rcordId":"Va4AAAV7BzEuefsF","realrate":90.4,"recycleamount":991528.4,"settamount":60945,"state":3,"subsidies":0,"veterdurgsettamt":34747.97}],"msg":"查询成功!"}
        //结算记录rcordid进行base64加密
        List<BalanceRecord> balanceRecords =JsonPluginsUtil.jsonTOList(jsonStr,BalanceRecord.class);
        for (int i=0;i<balanceRecords.size();i++){
            BalanceRecord balanceRecord1=balanceRecords.get(i);
            balanceRecord1.setRcordId(Image2Base64Util.getBase64Encoder(balanceRecord1.getRcordId()));
        }
        return balanceRecords;
    }

    @Override
    public BalanceRecord viewBalanceRecord(String batchId,String rcordId) throws Exception {
        if ( C.checkNullOrEmpty(rcordId) && C.checkNullOrEmpty(batchId))
            throw  new Exception("未传入条件Id!");
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("rcordId",rcordId);
        param.put("batchId",batchId);
        map.put("source",Commonconst.WX_Flag);//微信
        map.put("data",param);
        String data= JSONObject.fromObject(map).toString();
        String jsonStr = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_VIEW_SETTLEBILL);
        //{"code":"0000","data":{"avgWeight":7.72,"batchId":"Va4AAAWIE6dSsdKc","batchName":"叶飞004","billStatus":"已删除","billStatusIndex":"90","bizdate":"2019-04-25","feedsettamount":408612,"lrbamount":0,"material":"断奶苗","materialId":"Va4AAAAKl5TqXG71","meatrate":"-13.53","number":"JSD1904250001","payableamount":63245.16,"payforcustamount":0,"payment":0,"pigamount":404688,"rcordId":"Va4AAAidCMwuefsF","realrate":0,"settamount":63245.16,"state":3,"subsidies":905573.54,"veterdurgsettamt":29773.54},"msg":"查询成功!"}
        BalanceDetail balanceDetail = JsonPluginsUtil.jsonToBean(jsonStr, BalanceDetail.class);
        BalanceRecord balanceRecord=JsonPluginsUtil.jsonToBean(jsonStr,BalanceRecord.class);
        balanceRecord.setBalanceDetail(balanceDetail);
        return balanceRecord;
    }

    @Override
    public String singer(BalanceRecord BalanceRecord) throws Exception {
        Map<String, Object> map = new HashMap<>();
        //map.put("userId",userId);
        map.put("source",Commonconst.WX_Flag);//微信
        map.put("data",BalanceRecord);
        String data= JSONObject.fromObject(map).toString();
        String jsonStr = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_SIGNER_SETTLEBILL);
        return jsonStr;
    }

    @Override
    public String cancelApply(String rcordId) throws Exception {
        if ( C.checkNullOrEmpty(rcordId))
        throw  new Exception("未传入rcordId!");
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("rcordId",rcordId);
        map.put("source",Commonconst.WX_Flag);//微信
        map.put("data",param);
        String data= JSONObject.fromObject(map).toString();
        String jsonStr = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_DELETE_SETTLEBILL);
        return jsonStr;
    }
}
