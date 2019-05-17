package com.zb.byb.controller;


import com.github.pagehelper.PageInfo;
import com.zb.byb.common.C;
import com.zb.byb.common.Commonconst;
import com.zb.byb.entity.*;
import com.zb.byb.entity.DeathApply;
import com.zb.byb.entity.FeedRecord;
import com.zb.byb.entity.FileEntry;
import com.zb.byb.service.BatchRecordService;
import com.zb.byb.service.DeathApplyService;
import com.zb.byb.util.DateUtil;
import com.zb.byb.util.HttpConnectionUtil;
import com.zb.byb.util.Image2Base64Util;
import com.zb.framework.common.entity.Message;
import com.zb.framework.common.entity.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 死亡申报
 */
@RestController
@RequestMapping("/api/deathApply")
public class DeathApplyController {
    @Autowired
    private BatchRecordService batchRecordService;
    @Autowired
    private DeathApplyService deathApplyService;
    @ApiOperation("保存死亡申报")
    @PostMapping("/save")
    public ResponseEntity<?> deathApply(@RequestBody(required = false) DeathApply deathApply, HttpServletRequest request) throws IOException {
        //获取userId
        String userId=(String) request.getSession().getAttribute("userId");
//        userId = "Va4AAAdGxznMns7U";
        if (deathApply.getServerIds() != null && deathApply.getServerIds().size() > 0 )
        {
            List<FileEntry> list=new ArrayList<>();
            for (String serverId : deathApply.getServerIds()) {
                File file = HttpConnectionUtil.downloadWxImg(serverId);
                try {
                    if (file == null) { 
                        throw new Exception("获取文件失败");
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                    Message message = new Message();
                    message.setCode(C.parseStr(Commonconst.FailStatus));
                    message.setMessage(e.getMessage());
                    return ResponseEntity.build(Commonconst.FailStatus, message);
                }
                String base64Img = Image2Base64Util.fileToBase64(file);
                FileEntry fileEntry = new FileEntry();
                fileEntry.setImgContent(base64Img);
                fileEntry.setImgType("jpg");
                list.add(fileEntry);
            }
            deathApply.setImgUrl(list);
        }
        try {
            if(C.checkNullOrEmpty(userId)){
                throw new Exception("未传人养户id");
            }
            //解密成功
            String batchId=Image2Base64Util.getBase64Decoder(deathApply.getBatchId());
            deathApply.setBatchId(batchId);
            String backData= deathApplyService.deathApply(deathApply,userId);
            return ResponseEntity.buildSuccess(backData);
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }
    }
    @ApiOperation("获取死亡申报记录")
    @GetMapping("/list")
    public ResponseEntity<List<DeathApply>> getList(String starttime,String endtime,String state,Integer pageNumber,Integer pageSize, HttpServletRequest request){
        //获取userId
        String userId=(String) request.getSession().getAttribute("userId");
        DeathApply deathApply=new DeathApply();
        deathApply.setStarttime(starttime);
        deathApply.setEndtime(endtime);
        deathApply.setPageSize(pageSize);
        deathApply.setState(state);
        deathApply.setPageNumber(pageNumber);
        try {
            List<DeathApply> list= deathApplyService.getDeathApplyRecord(userId,deathApply);
            PageInfo<FeedRecord> info = new PageInfo(list);
            return ResponseEntity.buildSuccess(info);
            //return ResponseEntity.buildSuccess(batchRecordService.viewBatchRecord(batchId,openId));
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }

    }
    @ApiOperation("查看死亡申报记录详情")
    @GetMapping("/queryInfoById")
    public ResponseEntity<Death> queryInfoById(String rcordId)
    {
        try{
            if (C.checkNull(rcordId))
                throw new Exception("未传入rcordId");
            return ResponseEntity.buildSuccess(deathApplyService.getDeathApplyRecordbyId(Image2Base64Util.getBase64Decoder(rcordId)));
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

    @ApiOperation("取消死亡申报")
    @GetMapping("/cancleById")
    public ResponseEntity<DeathApply> cancleById(String rcordId)
    {
        try{
            if (C.checkNull(rcordId))
                throw new Exception("未传入rcordId.");
            return ResponseEntity.buildSuccess(deathApplyService.cancleDeathApply(Image2Base64Util.getBase64Decoder(rcordId)));
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

    @ApiOperation("死亡签名")
    @PostMapping("/singer")
    public ResponseEntity<DeathApply> singer(String rcordId, @RequestBody FileEntry fileEntry)
    {
        try{
        fileEntry= Image2Base64Util.subBase64(fileEntry);
        List<FileEntry> signerList=new ArrayList<>();
        signerList.add(fileEntry);
        DeathApply deathApply=new DeathApply();
        deathApply.setRcordId(Image2Base64Util.getBase64Decoder(rcordId));
        deathApply.setSignerList(signerList);
            if (C.checkNull(rcordId))
                throw new Exception("未传入rcordId.");
            String s = deathApplyService.signerDeathApply(deathApply);
            if (!"0000".equals(JSONObject.fromObject(s).getString("code")))
            throw new Exception("签名失败");
            return ResponseEntity.buildSuccess(s);
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


    @ApiOperation("初始化死亡日龄")
    @GetMapping("/initDieDay")
    public ResponseEntity<?> initDieDay(String dieDate,String birthday)
    {
        if(dieDate==null || birthday==null){
            return ResponseEntity.build(400, "日期不能为空",null);
        }
        try{
            return ResponseEntity.buildSuccess(DateUtil.getBetweenDay(DateUtil.parseDate(dieDate,"yyyy-MM-dd"),DateUtil.parseDate(birthday,"yyyy-MM-dd")));
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
