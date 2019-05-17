package com.zb.byb.controller;

import com.github.pagehelper.PageInfo;
import com.zb.byb.common.C;
import com.zb.byb.common.Commonconst;
import com.zb.byb.entity.FileEntry;
import com.zb.byb.entity.SingerTM;
import com.zb.byb.entity.TouMiao;
import com.zb.byb.service.TouMiaoService;
import com.zb.byb.util.BaseController;
import com.zb.byb.util.Image2Base64Util;
import com.zb.framework.common.entity.Message;
import com.zb.framework.common.entity.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 投苗申请
 */
@RestController
@RequestMapping("/api/toumiao")
public class TouMiaoController extends BaseController {
    @Autowired
    private TouMiaoService touMiaoService;

    @ApiOperation("保存投苗申请")
    @PostMapping("/saveToumiaoApply")
    @ResponseBody
    public ResponseEntity<?> saveToumiaoApply(HttpServletRequest request, HttpServletResponse response, TouMiao touMiao) {

        try{
            String custId = C.parseStr(request.getSession().getAttribute("custId"));
            touMiao.setCustId(custId);

            return ResponseEntity.buildSuccess(touMiaoService.saveInfo(touMiao));
        }
        catch (Exception e)
        {
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }
    }

    @ApiOperation("初始化投苗数据")
    @GetMapping("/queryTouMiaoInitData")
    @ResponseBody
    public ResponseEntity<TouMiao> queryTouMiaoInitData(HttpServletRequest request)
    {
        try{

            String custId = C.parseStr(request.getSession().getAttribute("custId"));
            return ResponseEntity.buildSuccess(touMiaoService.queryListInitData(custId));
        }
        catch (Exception e)
        {
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }
    }

    @ApiOperation("根据养户id查询到投苗记录")
    @GetMapping("/queryTouMiaoRecordList")
    @ResponseBody
    public ResponseEntity<?> queryTouMiaoRecordList(HttpServletRequest request, TouMiao touMiao)
    {
        try{

            String custId = C.parseStr(request.getSession().getAttribute("custId"));
            List list = touMiaoService.queryInfoRecordList(custId, touMiao);
            PageInfo<TouMiao> info = new PageInfo(list);
            return ResponseEntity.build(200,new Message(), info);
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

    @ApiOperation("根据id查询到对象信息")
    @GetMapping("/queryInfoById")
    @ResponseBody
    public ResponseEntity<TouMiao> queryInfoById(String recordId)
    {
        try{
            if (C.checkNullOrEmpty(recordId))
                throw new Exception("未传入记录id");

            return ResponseEntity.buildSuccess(touMiaoService.queryInfoById(Image2Base64Util.getBase64Decoder(recordId)));
        }
        catch (Exception e)
        {
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }
    }

    @ApiOperation("投苗签名")
    @PostMapping("/signerTouMiao")
    @ResponseBody
    public ResponseEntity<TouMiao> signerTouMiao(@RequestBody SingerTM fileEntry,HttpServletRequest request)
    {
        String userId=(String)request.getSession().getAttribute("userId");
//        System.out.println(fileEntry.getFileEntry());
        //处理投苗签名base64
        Image2Base64Util.subBase64(fileEntry.getFileEntry());

        List<FileEntry> signerList=new ArrayList<>();
        signerList.add(fileEntry.getFileEntry());
        TouMiao touMiao =new TouMiao();
        touMiao.setSignerList(signerList);
        try{
            touMiao.setRcordId(Image2Base64Util.getBase64Decoder(fileEntry.getRcordId()));
            if (C.checkNullOrEmpty(userId))
                throw new Exception("未登入");
            return ResponseEntity.buildSuccess(touMiaoService.singerTouMiao(touMiao));
        }
        catch (Exception e)
        {
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }
    }

    @ApiOperation("取消投苗申请")
    @GetMapping("/cancleTouMiao")
    @ResponseBody
    public ResponseEntity<TouMiao> cancleTouMiao(String rcordId,HttpServletRequest request)
    {
        String userid=(String)request.getSession().getAttribute("userId");
        try{
            if (C.checkNullOrEmpty(userid))
                throw new Exception("未登入");
            return ResponseEntity.buildSuccess(touMiaoService.cancleTouMiao(Image2Base64Util.getBase64Decoder(rcordId)));
        }
        catch (Exception e)
        {
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }
    }

}
