package com.zb.byb.controller;

import com.zb.byb.common.C;
import com.zb.byb.common.Commonconst;
import com.zb.byb.entity.*;
import com.zb.byb.service.ReceivedRecordService;
import com.zb.byb.util.Image2Base64Util;
import com.zb.framework.common.entity.Message;
import com.zb.framework.common.entity.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 领料记录
 */
@RestController
@RequestMapping("/api/receiveRecord")
public class ReceiveRecordController {
    @Autowired
    private ReceivedRecordService receivedRecordService;
    @ApiOperation("获取领料记录")
    @GetMapping("/pigwashRecordlist")
    public ResponseEntity<List<PigwashRecord>> getPigwashRecordList(){

        return ResponseEntity.buildSuccess(null);
    }

    @ApiOperation("获取领药记录")
    @GetMapping("/drugRecordlist")
    public ResponseEntity<List<DrugRecord>> getDrugRecordList(){

        return ResponseEntity.buildSuccess(null);
    }

    @ApiOperation("获取领用记录")
    @GetMapping("/receivedRecord")
    public ResponseEntity<?> getReceivedRecordList(String batchId,String type,Integer pageNumber,Integer pageSize,String state,String starttime,String endtime,HttpServletRequest request){
        String userId = (String)request.getSession().getAttribute("userId");
        //batchId="TsVQKmc6g4XgU5oBWApJqFKx0pw=";
        ReceivedRecord receivedRecord=new ReceivedRecord();
        receivedRecord.setPageNumber(pageNumber);
        receivedRecord.setPageSize(pageSize);
        receivedRecord.setState(state);
        receivedRecord.setEndtime(endtime);
        receivedRecord.setStarttime(starttime);

        receivedRecord.setType(type);

        //receivedRecord.setBeastDrug();
        try {
            receivedRecord.setBatchId(Image2Base64Util.getBase64Decoder(batchId));
            ReceivedRecord received = receivedRecordService.getReceivedList(receivedRecord);
            return ResponseEntity.buildSuccess(received);
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }
    }
}
