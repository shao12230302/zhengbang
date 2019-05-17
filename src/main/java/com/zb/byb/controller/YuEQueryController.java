package com.zb.byb.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zb.byb.common.C;
import com.zb.byb.common.Commonconst;
import com.zb.byb.entity.DealDetail;
import com.zb.byb.entity.YuE;
import com.zb.byb.service.YuEService;
import com.zb.framework.common.entity.Message;
import com.zb.framework.common.entity.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


/**
 * 余额信息
 */
@RestController
@RequestMapping("/api/yuEQuery")
public class YuEQueryController {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private YuEService yuEService;
    @ApiOperation("获取余额信息")
    @GetMapping("/query")
    public ResponseEntity<YuE> yuEQuery(String starttime,String endtime,HttpServletRequest request){
        YuE yuE=new YuE();
        yuE.setEndtime(endtime);
        yuE.setStarttime(starttime);
        String userId=(String) request.getSession().getAttribute("userId");
        try {
            String backData = yuEService.queryYuE(yuE,userId);
            String data=JSONObject.fromObject(backData).getString("data");
            YuE yuE1 = objectMapper.readValue(data,YuE.class);
            ResponseEntity<YuE> resp=new ResponseEntity<>();
            resp.setData(yuE1);
            return resp;
        } catch (Exception e) {
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }
    }
}
