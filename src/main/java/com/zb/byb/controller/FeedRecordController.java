package com.zb.byb.controller;

import com.github.pagehelper.PageInfo;
import com.zb.byb.common.C;
import com.zb.byb.common.Commonconst;
import com.zb.byb.entity.Batch;
import com.zb.byb.entity.FeedRecord;

import com.zb.byb.entity.Pigwash;
import com.zb.byb.service.FeedRecordService;

import com.zb.byb.util.Image2Base64Util;
import com.zb.framework.common.entity.Message;
import com.zb.framework.common.entity.ResponseEntity;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 饲喂记录
 */
@RestController
@RequestMapping("/api/feedRecord")
public class FeedRecordController {
    @Autowired
    private FeedRecordService feedRecordService;
    @ApiOperation("保存饲喂记录")
    @PostMapping("/save")
    public ResponseEntity<?> saveFeedRecord(@RequestBody(required = false) FeedRecord feedRecord, HttpServletRequest request) {
        //获取userId
        String userId=(String) request.getSession().getAttribute("userId");

        try {
            //解密
            feedRecord.setBatchId(Image2Base64Util.getBase64Decoder(feedRecord.getBatchId()));
            return ResponseEntity.buildSuccess(feedRecordService.addFeedRecord(feedRecord,userId));
        } catch (Exception e) {
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }
    }
    @ApiOperation("获取饲料列表")
    @GetMapping("/getPigwashList")
    public ResponseEntity<?> getPigwashList(Batch batch, HttpServletRequest request) {
        try {
            String batchId=Image2Base64Util.getBase64Decoder(batch.getId());
            return ResponseEntity.buildSuccess(feedRecordService.pigwashList(batchId));
        } catch (Exception e) {
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }
    }

    @ApiOperation("获取饲喂记录")
    @GetMapping("/list")
    public ResponseEntity<List<FeedRecord>> getList(String starttime,String endtime,String state,Integer pageNumber,Integer pageSize,HttpServletRequest request){
        String custId=(String) request.getSession().getAttribute("userId");
        FeedRecord feedRecord=new FeedRecord();
        if (pageNumber==null){
        }else{
            feedRecord=new FeedRecord();
            feedRecord.setStarttime(starttime);
            feedRecord.setEndtime(endtime);
            feedRecord.setPageNumber(pageNumber);
            feedRecord.setPageSize(pageSize);
            feedRecord.setState(state);
        }
        try {
            if (C.checkNull(custId)){
                throw new Exception("无养户id");
            }
            List<FeedRecord> list = feedRecordService.queryFeedRecordList(custId,feedRecord);
            if(list==null || list.size()==0) {
                return ResponseEntity.build(200, "无记录", null);
            }
            for(int i=0;i<list.size();i++){
                list.get(i).setBatchId(list.get(i).getFeedList().get(0).getBatchId());
                list.get(i).setBatchName(list.get(i).getFeedList().get(0).getBatchName());
            }
            List<Pigwash> feedList = list.get(0).getFeedList();
            PageInfo<FeedRecord> info = new PageInfo(list);
            return ResponseEntity.build(200,new Message(),info);
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }
    }

    @ApiOperation("根据id查询到对象信息")
    @GetMapping("/queryInfoById")
    @ResponseBody
    public ResponseEntity<FeedRecord> queryInfoById(String rcordId)
    {
        try{
            if (C.checkNull(rcordId))
                throw new Exception("未传入rcordId.");
            FeedRecord feedRecord = feedRecordService.queryFeedRecordbyRcordId(Image2Base64Util.getBase64Decoder(rcordId));
            if (null==feedRecord){
                return ResponseEntity.build(100, "无法查询到数据", null);
            }
            List<Pigwash> feedList = feedRecord.getFeedList();
            ResponseEntity<FeedRecord> recordResponseEntity=new ResponseEntity<>();
            recordResponseEntity.setData(feedRecord);
            return recordResponseEntity;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }
    }

    @ApiOperation("取消记录")
    @GetMapping("/cancleById")
    @ResponseBody
    public ResponseEntity<FeedRecord> cancleById(String rcordId)
    {
        try{
            if (C.checkNull(rcordId))
                throw new Exception("未传入rcordId.");
            String s = feedRecordService.cancleFeedRecord(Image2Base64Util.getBase64Decoder(rcordId));
            return ResponseEntity.build(100, "取消成功", null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }
    }
}
