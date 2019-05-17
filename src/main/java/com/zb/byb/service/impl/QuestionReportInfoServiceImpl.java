package com.zb.byb.service.impl;

import com.zb.byb.common.C;
import com.zb.byb.common.Commonconst;
import com.zb.byb.entity.QuestionReportInfo;
import com.zb.byb.service.MyInfoService;
import com.zb.byb.service.QuestionReportInfoService;
import com.zb.byb.util.BackTransmitUtil;
import com.zb.byb.util.JsonPluginsUtil;
import com.zb.byb.util.MethodName;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 问题反馈
 * 作者：谢李
 */
@Service
public class QuestionReportInfoServiceImpl implements QuestionReportInfoService {
    @Autowired
    private MyInfoService myInfoService;
    @Autowired
    private BackTransmitUtil backTransmitUtil;
    @Override
    public String saveQuestionReport(QuestionReportInfo info) throws Exception {

        Map<String, Object> map = new HashMap<>();
        map.put("custId", info.getCustId());
        map.put("source", Commonconst.WX_Flag);

        map.put("data", info);
        String data = JSONObject.fromObject(map).toString();
        String jsonBackStr = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_SAVE_PROBLEMFEEDBACK);
        String id = JsonPluginsUtil.isRequestSuccessBackId(jsonBackStr);
        return id;
    }

    @Override
    public List<QuestionReportInfo> queryNormalQuestionList(String id) throws Exception {
        if (C.checkNullOrEmpty(id))
            return new ArrayList<>();

        QuestionReportInfo queryInfo = new QuestionReportInfo();
        queryInfo.setCustId(id);
        Map<String, Object> map = new HashMap<>();
        map.put("custId", id);
        map.put("data", queryInfo);
        map.put("pageNumber","1");
        map.put("pageSize","1000");

        // 要传入数据进行转化
        String data= JSONObject.fromObject(map).toString();
        //{"pageNumber":"1","data":{"bizdate":"","custId":"Va4AAAdGxznMns7U","custname":"","details":"","extendsOne":"","id":"","order":"","pageNumber":1,"pageSize":1000,"rcordId":"","remark":"","replydate":"","replydetails":"","replypersonid":"","replypersonname":""},"custId":"Va4AAAdGxznMns7U","pageSize":"1000"}
        String jsonData = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_QUERY_PROBLEMFEEDBACK);
        return JsonPluginsUtil.jsonToBeanList(jsonData, QuestionReportInfo.class);
    }

    @Override
    public QuestionReportInfo queryQuestionInfoById(String id) throws Exception {
        if (C.checkNullOrEmpty(id))
            return new QuestionReportInfo();

        QuestionReportInfo queryInfo = new QuestionReportInfo();
        queryInfo.setRcordId(id);
        Map<String, Object> map = new HashMap<>();
        map.put("data", queryInfo);

        // 要传入数据进行转化
        String data= JSONObject.fromObject(map).toString();
        String jsonData = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_VIEW_PROBLEMFEEDBACK);
       //{"code":"0000","data":{"bizdate":"2019-05-06","custid":"Va4AAAO6drnMns7U","custname":"胡亿龙","details":"思考dont","rcordId":"Va4AAAiiQDt/YF2u","replydate":"2019-05-06","replydetails":"666","replypersonid":"IkE9MNuvTZ2/OleSqVTYJxO33n8=","replypersonname":"冯常耀"},"msg":"查询成功!"}
        return JsonPluginsUtil.jsonToBean(jsonData, QuestionReportInfo.class);
    }

    @Override
    public boolean deleteQuestionInfoById(String id) throws Exception {
        if (C.checkNullOrEmpty(id))
            return false;

        QuestionReportInfo queryInfo = new QuestionReportInfo();
        queryInfo.setId(id);
        Map<String, Object> map = new HashMap<>();
        map.put("openId", Commonconst.OpenId);
//        map.put("custId", Commonconst.CustId);
        map.put("data", queryInfo);

        // 要传入数据进行转化
        String data= JSONObject.fromObject(map).toString();
        String jsonData = backTransmitUtil.invokeFunc(data, MethodName.METHOD_NAME_DELETE_PROBLEMFEEDBACK);
        return true;
    }
}
