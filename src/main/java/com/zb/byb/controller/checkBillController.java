package com.zb.byb.controller;



import com.github.pagehelper.PageInfo;
import com.zb.byb.common.C;
import com.zb.byb.common.Commonconst;
import com.zb.byb.entity.BillInfo;
import com.zb.byb.entity.TouMiao;
import com.zb.byb.service.BillService;
import com.zb.byb.service.impl.BillServiceImpl;
import com.zb.byb.util.HtmlToImageUtil;
import com.zb.byb.util.Image2Base64Util;
import com.zb.framework.common.entity.Message;
import com.zb.framework.common.entity.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

/**
 * @Function: 对账单控制类
 * @Author: shaoys
 * @Date: Created in 1:22 2019/5/12
 **/
@RestController
@RequestMapping("/api/bill")
public class checkBillController {
    @Autowired
    private BillService billService;

    @ApiOperation("对账单查询列表")
    @GetMapping("/queryBillRecordList")
    @ResponseBody
    public ResponseEntity<?> queryBillRecordList(HttpServletRequest request, BillInfo info)
    {
        try{
            String custId = C.parseStr(request.getSession().getAttribute("custId"));
            info.setCustId(custId);
            List list = billService.queryInfoRecordList(info);
            PageInfo<BillInfo> pageInfo = new PageInfo(list);
            return ResponseEntity.build(200,new Message(), pageInfo);
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

    @ApiOperation("对账单记录查询")
    @GetMapping("/queryBillRecordById")
    @ResponseBody
    public ResponseEntity<?> queryBillRecordById(HttpServletRequest request, BillInfo info)
    {
        try{
            String custId = C.parseStr(request.getSession().getAttribute("custId"));
            info.setCustId(custId);
            info.setDepartment(C.parseStr(request.getSession().getAttribute("servicedep")));
            String htmlTemplate = billService.queryBillRecordById(info);
            if (C.checkNullOrEmpty(htmlTemplate))
                throw new Exception("未获取账单数据");

            byte[] bytes = HtmlToImageUtil.html2png(Color.white, htmlTemplate, new EmptyBorder(0, 0, 0, 0), HtmlToImageUtil.Width, HtmlToImageUtil.Height);
//            System.out.println("data:image/png;base64," + new String(Base64.encodeBase64(bytes)));
            return ResponseEntity.build(200,new Message(), "data:image/png;base64," + new String(Base64.encodeBase64(bytes)));
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
