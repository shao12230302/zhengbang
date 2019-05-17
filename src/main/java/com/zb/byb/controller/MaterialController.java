package com.zb.byb.controller;

import com.github.pagehelper.PageInfo;
import com.zb.byb.common.C;
import com.zb.byb.common.Commonconst;
import com.zb.byb.entity.*;
import com.zb.byb.service.DrugApplyService;
import com.zb.byb.service.EquipmentApplyService;
import com.zb.byb.service.FeedApplyService;
import com.zb.byb.util.HttpConnectionUtil;
import com.zb.byb.util.Image2Base64Util;
import com.zb.framework.common.entity.Message;
import com.zb.framework.common.entity.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 物资申请
 */
@RestController
@RequestMapping("/api/material")
public class MaterialController {

    @Autowired
    private DrugApplyService drugApplyService;
    @Autowired
    private EquipmentApplyService equipmentApplyService;
    @Autowired
    private FeedApplyService feedApplyService;


    /**
     * 我要领料
     * @param feedApply
     * @return
     */
    @ApiOperation("保存领料申请")
    @PostMapping("/saveFeedApply")//@RequestBody(required = false)
    public ResponseEntity<?> feedApply(@RequestBody FeedApply feedApply,HttpServletRequest request) {
        String userId=(String) request.getSession().getAttribute("userId");
        try {
            if (C.checkNull(userId))
                throw new Exception("未传入养户id");
            String batchId=Image2Base64Util.getBase64Decoder(feedApply.getBatchId());
            feedApply.setBatchId(batchId);
            String id= feedApplyService.feedApply(feedApply,userId);
            return ResponseEntity.buildSuccess(id);
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }
    }

    @ApiOperation("获取领料申请记录")
    @GetMapping("/feedList")
    public ResponseEntity<List<FeedApply>> getFeedList(FeedApply feedApply,HttpServletRequest request){
        String userId=(String) request.getSession().getAttribute("userId");
//        System.out.println(userId);
        try {
            if (C.checkNull(userId))
                throw new Exception("未传入养户id");
            return ResponseEntity.buildSuccess(feedApplyService.queryFeedApply(feedApply,userId));
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }
    }

    @ApiOperation("查看领料申请信息")
    @GetMapping("/viewInfoById")
    public ResponseEntity<List<FeedApply>> viewInfobyId( String rcordId,HttpServletRequest request){
        String userId=(String) request.getSession().getAttribute("userId");
        try {
            return ResponseEntity.buildSuccess(feedApplyService.viewFeedApply(Image2Base64Util.getBase64Decoder(rcordId)));
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }
    }

    @ApiOperation("提交领料签名")
    @PostMapping("/signerFeedApply")//@RequestBody(required = false)
    public ResponseEntity<List<FeedApply>> signer(String rcordId,@RequestBody FileEntry fileEntry, HttpServletRequest request){
        FeedApply feedApply=new FeedApply() ;
        String userId=(String) request.getSession().getAttribute("userId");
        fileEntry= Image2Base64Util.subBase64(fileEntry);
        List<FileEntry> signerList=new ArrayList<>();
        signerList.add(fileEntry);//存入实体
        feedApply.setSignerList(signerList);

        try {
            feedApply.setRcordId(Image2Base64Util.getBase64Decoder(rcordId));
            String data=feedApplyService.singer(feedApply);
            if (!"0000".equals(JSONObject.fromObject(data).getString("code")))
                throw new Exception("签名失败");
            return ResponseEntity.buildSuccess(data);
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }
    }

    @ApiOperation("取消领料申请")
    @GetMapping("/canclefeedApply")//@RequestBody(required = false)
    public ResponseEntity<List<FeedApply>> canclefeedApply(String rcordId, HttpServletRequest request){

        String userId=(String) request.getSession().getAttribute("userId");
        //空格处理
        try {
            String data=feedApplyService.cancleFeedApply(Image2Base64Util.getBase64Decoder(rcordId));
            if (!"0000".equals(JSONObject.fromObject(data).getString("code")))
                throw new Exception("取消失败");
            return ResponseEntity.buildSuccess(data);
        } catch (Exception e) {
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }
    }

    @ApiOperation("获取司机列表")
    @GetMapping("/getDriverList")//@RequestBody(required = false)
    public ResponseEntity<List<Driver>> getDriverList(Integer pageNumber,Integer pageSize,HttpServletRequest request){
        String custId=(String) request.getSession().getAttribute("userId");
        FeedApply feedApply=new FeedApply();
        feedApply.setPageNumber(pageNumber);
        feedApply.setPageSize(pageSize);
        try {
            List <Driver> driverList= feedApplyService.getDriverList(feedApply,custId);
            return ResponseEntity.buildSuccess(driverList);
        } catch (Exception e) {
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }
    }

    @ApiOperation("获取饲料列表")
    @GetMapping("/getFeedList")//@RequestBody(required = false)
    public ResponseEntity<List<LiLiaoInfo>> getFeedList(String batchId ,HttpServletRequest request){
        String custId=(String) request.getSession().getAttribute("userId");
        FeedApply feedApply=new FeedApply();
        try {
            batchId=Image2Base64Util.getBase64Decoder(batchId);
            feedApply.setBatchId(batchId);
            List <LiLiaoInfo> liLiaoInfoList= feedApplyService.getFeedList(feedApply);
            return ResponseEntity.buildSuccess(liLiaoInfoList);
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }
    }


    /**
     * 我要领药
     * @param drugApply
     * @return
     */
    @ApiOperation("保存领药申请")
    @PostMapping("/saveDrugApply")
    public ResponseEntity<?> saveDrugApply(HttpServletRequest request, @RequestBody DrugApply drugApply) throws IOException {
        try{
            File file = HttpConnectionUtil.downloadWxFile(drugApply.getServerId());
            String base64Amr = Image2Base64Util.fileToBase64(file);
            FileEntry fileEntry=new FileEntry();
            fileEntry.setImgContent(base64Amr);
            fileEntry.setImgType("mp3");
            List<FileEntry> list=new ArrayList<>();
            list.add(fileEntry);
            drugApply.setVoiceList(list);
            String custId = C.parseStr(request.getSession().getAttribute("custId"));
            drugApply.setCustId(custId);
            String batchId=Image2Base64Util.getBase64Decoder(drugApply.getBatchId());
            drugApply.setBatchId(batchId);
            return ResponseEntity.buildSuccess(drugApplyService.saveInfo(drugApply));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Message message = new Message();
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus,message,null);
        }
    }

    @ApiOperation("根据批次id查询药品选择列表，支持模糊查询")
    @GetMapping("/queryMaterialInfoByBatchId")
    public ResponseEntity<MaterialInfo> queryMaterialInfoByBatchId(HttpServletRequest request, MaterialInfo queryInfo)
    {
        String custId = C.parseStr(request.getSession().getAttribute("custId"));
        try{
            if (C.checkNull(custId))
                throw new Exception("未传入养户id");
            queryInfo.setBatchId(Image2Base64Util.getBase64Decoder(queryInfo.getBatchId()));
            queryInfo.setCustId(custId);
            List<MaterialInfo> list = drugApplyService.queryMaterialListByFuzzyKey(queryInfo);
            PageInfo page = new PageInfo(list);
            return ResponseEntity.buildSuccess(page);
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

    @ApiOperation("初始化我要领药数据传入custId")
    @GetMapping("/queryDrugApplyInitData")
    public ResponseEntity<DrugApply> queryDrugApplyInitData(HttpServletRequest request)
    {
        String custId = C.parseStr(request.getSession().getAttribute("custId"));
        try{
            if (C.checkNull(custId))
                throw new Exception("未传入养户id");

            return ResponseEntity.buildSuccess(drugApplyService.queryListInitData(custId));
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

    @ApiOperation("领药申请记录列表")
    @GetMapping("/queryDrugApplyRecordList")
    public ResponseEntity<?> queryDrugApplyRecordList(HttpServletRequest request, DrugApply queryInfo,HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String custId = C.parseStr(request.getSession().getAttribute("custId"));
        try{
            if (C.checkNull(custId))
                throw new Exception("未传入养户id");

            List<DrugApply> list = drugApplyService.queryInfoRecordList(custId,queryInfo);
            PageInfo page = new PageInfo(list);
            return ResponseEntity.buildSuccess(page);
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

    @ApiOperation("查看领药申请详情")
    @GetMapping("/queryDrugApplyInfoById")
    @ResponseBody
    public ResponseEntity<DrugApply> queryDrugApplyInfoById(HttpServletRequest request, String rcordId)
    {
        String custId = C.parseStr(request.getSession().getAttribute("custId"));
        try{
            if (C.checkNull(rcordId))
                throw new Exception("未传入id");
            DrugApply drugApply = drugApplyService.queryInfoById(Image2Base64Util.getBase64Decoder(rcordId));
            return ResponseEntity.buildSuccess(drugApply);
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

    @ApiOperation("提交领药签名")
    @PostMapping("/signerDrugApply")//@RequestBody(required = false)
    public ResponseEntity<?> signerDrugApply(String rcordId,@RequestBody FileEntry fileEntry, HttpServletRequest request){
        DrugApply drugApply=new DrugApply() ;
        String userId=(String) request.getSession().getAttribute("userId");
        fileEntry= Image2Base64Util.subBase64(fileEntry);
        List<FileEntry> signerList=new ArrayList<>();
        signerList.add(fileEntry);//存入实体
        drugApply.setSignerList(signerList);
        try {
            drugApply.setRcordId(Image2Base64Util.getBase64Decoder(rcordId));
            String data=drugApplyService.singer(drugApply);
            if (!"0000".equals(JSONObject.fromObject(data).getString("code")))
                return ResponseEntity.build(400, "签名失败", null);
            return ResponseEntity.buildSuccess(data);
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }
    }

    @ApiOperation("删除领药对象信息通过id")
    @GetMapping("/deleteDrugApplyInfoById")
    @ResponseBody
    public ResponseEntity<DrugApply> deleteDrugApplyInfoById(String id)
    {

        try{
            ;
            if (C.checkNull(id))
                throw new Exception("未传入id");
            return ResponseEntity.buildSuccess(drugApplyService.deleteInfoById(Image2Base64Util.getBase64Decoder(id)));
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


    /**
     * 设备领用
     * @param
     * @return
     */
    @ApiOperation("保存设备申请")
    @PostMapping("/saveEquipmentApply")
    public ResponseEntity<?> saveEquipmentApply(HttpServletRequest request, @RequestBody EquipmentApply equipmentApply) {
        try{
            String custId = C.parseStr(request.getSession().getAttribute("custId"));
            equipmentApply.setCustId(custId);
            return ResponseEntity.buildSuccess(equipmentApplyService.saveInfo(equipmentApply));
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



    @ApiOperation("初始化设备申请数据")
    @GetMapping("/queryEquipmentApplyInitData")
    public ResponseEntity queryEquipmentApplyInitData(HttpServletRequest request)
    {
        String custId = C.parseStr(request.getSession().getAttribute("custId"));
        try{
            if (C.checkNull(custId))
                throw new Exception("未传入id");
            return ResponseEntity.buildSuccess(equipmentApplyService.queryListInitData(custId));
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

    @ApiOperation("根据养户id查询到设备记录列表")
    @GetMapping("/queryEquipmentApplyRecordList")
    public ResponseEntity<?> queryEquipmentApplyRecordList(HttpServletRequest request, EquipmentApply info)
    {
        String custId = C.parseStr(request.getSession().getAttribute("custId"));
        try{
            if (C.checkNull(custId))
                throw new Exception("未传入id");
            info.setCustId(custId);
            List<EquipmentApply> list = equipmentApplyService.queryInfoRecordList(info);
            PageInfo page = new PageInfo(list);
            return ResponseEntity.buildSuccess(page);
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

    @ApiOperation("查询委托人列表")
    @GetMapping("/queryEntrustList")
    public ResponseEntity<?> queryEntrustList(HttpServletRequest request, EquipmentApply info)
    {
        String custId = C.parseStr(request.getSession().getAttribute("custId"));
        try{
            if (C.checkNull(custId))
                throw new Exception("未传入id");

            info.setCustId(custId);
            List<EntrustInfo> list = equipmentApplyService.queryEntrustList(info);
            PageInfo page = new PageInfo(list);
            return ResponseEntity.buildSuccess(page);
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


    @ApiOperation("根据id查询到设备领用详情")
    @GetMapping("/queryEquipmentApplyInfoById")
    @ResponseBody
    public ResponseEntity<EquipmentApply> queryEquipmentApplyInfoById(String rcordId)
    {
        try{
            if (C.checkNull(rcordId))
                throw new Exception("未传入id");
            return ResponseEntity.buildSuccess(equipmentApplyService.queryInfoById(Image2Base64Util.getBase64Decoder(rcordId)));
        }
        catch (Exception e)
        {

            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }
    }

    @ApiOperation("删除设备对象信息通过id")
    @GetMapping("/deleteEquipmentApplyInfoById")
    @ResponseBody
    public ResponseEntity<?> deleteEquipmentApplyInfoById(String id)
    {
        try{
            if (C.checkNull(id))
                throw new Exception("未传入id");
            String data=equipmentApplyService.deleteInfoById(Image2Base64Util.getBase64Decoder(id));
            if (!"0000".equals(JSONObject.fromObject(data).getString("code"))){
                throw new Exception("取消失败");
            }
            return ResponseEntity.buildSuccess(data);
        }
        catch (Exception e)
        {

            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }
    }


    @ApiOperation("搜索设备列表")
    @GetMapping("/searchEquimp")
    public ResponseEntity<?> searchEquimp(HttpServletRequest request, String keyword)
    {
        String custId = C.parseStr(request.getSession().getAttribute("custId"));
        try{
            if (C.checkNull(custId))
                throw new Exception("未传入id");
            List<Equipment> list = equipmentApplyService.searchEquipment(keyword,custId);
            PageInfo page = new PageInfo(list);
            return ResponseEntity.buildSuccess(page);
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

    @ApiOperation("设备领用签名")
    @PostMapping("/signerEquipApply")
    public ResponseEntity<?> signerEquipApply(String rcordId, @RequestBody FileEntry fileEntry,
                                              HttpServletRequest request, HttpServletResponse response){
        String userId=(String) request.getSession().getAttribute("userId");
        fileEntry= Image2Base64Util.subBase64(fileEntry);
         EquipmentApply equipmentApply=new EquipmentApply();
        List<FileEntry> signerList=new ArrayList<>();
        signerList.add(fileEntry);//存入实体
        equipmentApply.setSignerList(signerList);
        try {
            equipmentApply.setRcordId(Image2Base64Util.getBase64Decoder(rcordId));
            String data=equipmentApplyService.signerEquipApply(equipmentApply);
            if (!"0000".equals(JSONObject.fromObject(data).getString("code")))
                return ResponseEntity.build(400, "签名失败", null);
            return ResponseEntity.buildSuccess(data);
        } catch (Exception e) {

            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message,null);
        }
    }
}
