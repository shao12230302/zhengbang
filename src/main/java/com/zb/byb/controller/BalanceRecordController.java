package com.zb.byb.controller;

import com.zb.byb.common.C;
import com.zb.byb.common.Commonconst;
import com.zb.byb.entity.Balance;
import com.zb.byb.entity.BalanceRecord;

import com.zb.byb.entity.FileEntry;
import com.zb.byb.service.BalanceService;
import com.zb.byb.util.Image2Base64Util;
import com.zb.framework.common.entity.Message;
import com.zb.framework.common.entity.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/balanceRecord")
public class BalanceRecordController {
    @Autowired
    private BalanceService balanceService;

    @ApiOperation("根据结算申请表单带出")
    @GetMapping("/initInfoByBatchId")
    public ResponseEntity<?> initInfoByBatchId(String batchId,HttpServletRequest request){
        String userId = (String) request.getSession().getAttribute("userId");
        //userId="Va4AAABJzw/Mns7U";

        try {
            ;//解密
            Balance balance=balanceService.initInfoByBatchId(Image2Base64Util.getBase64Decoder(batchId),userId);
            return ResponseEntity.buildSuccess(balance);
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }
    }

    @ApiOperation("保存结算申请")
    @PostMapping("/apply")
    public ResponseEntity<?> balanceApply(@RequestBody Balance balance,HttpServletRequest request){

        String userId = (String) request.getSession().getAttribute("userId");
        try {
            String batchId=balance.getBatchId();
            balance.setBatchId(Image2Base64Util.getBase64Decoder(batchId));
            String id=balanceService.balanceApply(balance,userId);
            return ResponseEntity.buildSuccess(id);
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }
    }

    @ApiOperation("获取结算申请记录")
    @GetMapping("/list")
    public ResponseEntity<List<BalanceRecord>> getList(String starttime,String endtime,String state,Integer pageNumber,Integer pageSize,HttpServletRequest request){
        String userId = (String) request.getSession().getAttribute("userId");
        BalanceRecord balanceRecord=new BalanceRecord();
        balanceRecord.setStarttime(starttime);
        balanceRecord.setEndtime(endtime);
        balanceRecord.setState(state);
        balanceRecord.setPageNumber(pageNumber);
        balanceRecord.setPageSize(pageSize);
        try {
            List<BalanceRecord> list=balanceService.getBalanceRecord(balanceRecord,userId);
            return ResponseEntity.buildSuccess(list);
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }

    }

    @ApiOperation("查看结算申请详情(结算记录传批次，申请详情传单据id)")
    @GetMapping("/viewInfoById")
    public ResponseEntity<List<BalanceRecord>> viewInfoById(String batchId,String rcordId,HttpServletRequest request){
        String userId = (String) request.getSession().getAttribute("userId");
        try {

            BalanceRecord balanceRecord=balanceService.viewBalanceRecord(Image2Base64Util.getBase64Decoder(batchId),Image2Base64Util.getBase64Decoder(rcordId));
            return ResponseEntity.buildSuccess(balanceRecord);
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }

    }

    @ApiOperation("取消结算申请")
    @GetMapping("/cancleApply")
    public ResponseEntity<List<BalanceRecord>> cancleApply(String rcordId,HttpServletRequest request){
        String userId = (String) request.getSession().getAttribute("userId");

        try {
            String id=balanceService.cancelApply(Image2Base64Util.getBase64Decoder(rcordId));
            return ResponseEntity.buildSuccess(id);
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }

    }

    @ApiOperation("签名提交")
    @PostMapping("/signer")
    public ResponseEntity<List<BalanceRecord>> signer(String rcordId,@RequestBody FileEntry fileEntry, HttpServletRequest request){
        String userId = (String) request.getSession().getAttribute("userId");
        
        fileEntry= Image2Base64Util.subBase64(fileEntry);
        List<FileEntry> signerList=new ArrayList<>();
        signerList.add(fileEntry);
        BalanceRecord balanceRecord=new BalanceRecord();
        balanceRecord.setSignerList(signerList);

        //userId="Va4AAABJzw/Mns7U";
        try {
            balanceRecord.setRcordId(Image2Base64Util.getBase64Decoder(rcordId));
            String id=balanceService.singer(balanceRecord );
            return ResponseEntity.buildSuccess(id);
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }

    }


}
