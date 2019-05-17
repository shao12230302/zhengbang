package com.zb.byb.controller;

import com.github.pagehelper.PageInfo;
import com.zb.byb.common.C;
import com.zb.byb.common.Commonconst;
import com.zb.byb.entity.DeathApply;
import com.zb.byb.entity.FeedRecord;
import com.zb.byb.entity.SaleNotice;
import com.zb.byb.service.SaleNoticeService;
import com.zb.byb.util.Image2Base64Util;
import com.zb.framework.common.entity.Message;
import com.zb.framework.common.entity.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 销售通知
 */
@RestController
@RequestMapping("/api/saleNotice")
public class SaleNoticeController {
    @Autowired
    private SaleNoticeService saleNoticeService;

    @ApiOperation("单个销售详情")
    @GetMapping("/viewSale")
    public ResponseEntity<?> querySale(String id) {

        try {

            return ResponseEntity.buildSuccess(saleNoticeService.getQuerySale(Image2Base64Util.getBase64Decoder(id)));
        }
        catch (Exception e)
        {
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }
    }
    @ApiOperation("查询销售列表")
    @GetMapping("/querySale")
    public ResponseEntity<?> viewSale(HttpServletRequest request) {
        try {
            String custId = C.parseStr(request.getSession().getAttribute("custId"));
//            String custId = "Va4AAABL9PPMns7U";
            List<SaleNotice> list = saleNoticeService.getSaleRecordList(custId);
            PageInfo page = new PageInfo(list);
            return ResponseEntity.buildSuccess(page);
        } catch (Exception e) {
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }
    }
}
