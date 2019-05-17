package com.zb.byb.controller;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zb.byb.common.C;
import com.zb.byb.common.Commonconst;
import com.zb.byb.common.Constants;
import com.zb.byb.entity.Batch;
import com.zb.byb.entity.BatchRecord;
import com.zb.byb.entity.DeathApply;
import com.zb.byb.entity.FeedRecord;
import com.zb.byb.service.BatchRecordService;
import com.zb.byb.service.TouMiaoService;
import com.zb.byb.util.*;
import com.zb.framework.common.entity.Message;
import com.zb.framework.common.entity.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 批次记录
 */
@RestController
@RequestMapping("/api/batchRecord")
public class BatchRecordController {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BatchRecordService batchRecordService;

    @ApiOperation("查看批次记录")
    @GetMapping("/list")
    public ResponseEntity<BatchRecord> getList(HttpServletRequest request,String batchid){
        String userId=(String) request.getSession().getAttribute("userId");
        //批次号

        try {
            BatchRecord batchRecord1= batchRecordService.viewBatchRecord(Image2Base64Util.getBase64Decoder(batchid),userId);

            ResponseEntity<BatchRecord> resp=new ResponseEntity<>();
            resp.setData(batchRecord1);
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }
    }
    @ApiOperation("获取批次列表下拉框")
    @GetMapping("/batchListList")
    public ResponseEntity<List<Batch>> getBatchList(HttpServletRequest request,Batch batch){//
        String userId=(String) request.getSession().getAttribute("userId");
        //userId="mRkwGN6DQgGNsONd+yMkV8yeztQ=";
//        System.out.println("=="+userId);
        try {
            List<Batch> list=batchRecordService.getBatchList(userId,batch);
            ResponseEntity<List<Batch>> resp=new ResponseEntity<>();
            resp.setData(list);
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }
    }

}
