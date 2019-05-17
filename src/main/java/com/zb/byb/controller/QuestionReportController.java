package com.zb.byb.controller;


import com.github.pagehelper.PageInfo;
import com.zb.byb.common.C;
import com.zb.byb.common.Commonconst;
import com.zb.byb.entity.QuestionReportInfo;
import com.zb.byb.service.QuestionReportInfoService;
import com.zb.framework.common.entity.Message;
import com.zb.framework.common.entity.ResponseEntity;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 问题反馈信息
 */
@RestController
@RequestMapping("/api/questionReport")
public class QuestionReportController {
    @Autowired
    private QuestionReportInfoService questionReportInfoService;

    @ApiOperation("保存问题反馈信息")
    @PostMapping("/saveQuestionReport")
    public ResponseEntity<?> saveQuestionReport(HttpServletRequest request,QuestionReportInfo info) {
        try {
            String custId = C.parseStr(request.getSession().getAttribute("custId"));
            info.setCustId(custId);
            return ResponseEntity.buildSuccess(questionReportInfoService.saveQuestionReport(info));
        }
        catch (Exception e)
        {
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }
    }

    @ApiOperation("常见问题查询列表，通过养户id")
    @GetMapping("/queryQuestionList")
    public ResponseEntity<?> queryQuestionList(HttpServletRequest request) {
        try {
            String custId = C.parseStr(request.getSession().getAttribute("custId"));
            List<QuestionReportInfo> list = questionReportInfoService.queryNormalQuestionList(custId);
            PageInfo page = new PageInfo(list);
            return ResponseEntity.buildSuccess(page);
        }
        catch (Exception e)
        {
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }
    }

    @ApiOperation("问题查询通过问题id")
    @GetMapping("/queryQuestionInfoById")
    public ResponseEntity<?> queryQuestionInfoById(String id) {
        try {
            return ResponseEntity.buildSuccess(questionReportInfoService.queryQuestionInfoById(id));
        }
        catch (Exception e)
        {
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }
    }

    @ApiOperation("删除问题通过问题id")
    @GetMapping("/deleteQuestionInfoById")
    public ResponseEntity<?> deleteQuestionInfoById(String id) {
        try {
            return ResponseEntity.buildSuccess(questionReportInfoService.deleteQuestionInfoById(id));
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
