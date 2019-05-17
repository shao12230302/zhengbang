package com.zb.byb.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zb.byb.common.C;
import com.zb.byb.common.Commonconst;
import com.zb.byb.common.Constants;
import com.zb.byb.entity.*;
import com.zb.byb.service.BatchRecordService;
import com.zb.byb.service.LoginService;
import com.zb.byb.service.MyInfoService;
import com.zb.byb.util.JsonPluginsUtil;
import com.zb.byb.util.RequestUtils;
import com.zb.framework.common.entity.Message;
import com.zb.framework.common.entity.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private LoginService loginService;
    @Autowired
    private MyInfoService myInfoService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BatchRecordService batchRecordService;


    @ApiOperation("登入")
    @GetMapping("/login")
    public ResponseEntity<?> login(HttpServletRequest request) {

        HttpSession session=  request.getSession();

        //获取openId,并存入session
        String openId= RequestUtils.getCookieByName(request, Constants.OPEN_ID);
        openId = "oNwsZuNGD66uuMy0hlYwCwngdETo";
        session.setAttribute("openId",openId);

        try {
            String json=myInfoService.viewMyInfo(openId);
            String myInfoStr = JsonPluginsUtil.getSuccessData(json);

            if (C.checkNullOrEmpty(myInfoStr))
                throw new Exception("登录失败，未获取个人信息");

            JSONObject jsonMap = JSONObject.parseObject(myInfoStr);

            String userId = jsonMap.getString("id");

            if (C.checkNullOrEmpty(userId)){
                throw new Exception("登录失败");
            }
            //养户id存入session
            session.setAttribute("userId",userId);
            session.setAttribute("fname", jsonMap.getString("fname"));
            session.setAttribute("custId", userId);
            session.setAttribute("cfwinternum", jsonMap.getString("cfwinternum"));
            session.setAttribute("servicedep", jsonMap.getString("servicedep"));
            //  批次
            Batch batch = new Batch();
            batch.setPageNumber(1);
            batch.setPageSize(50);
            List<Batch> list=batchRecordService.getBatchList(userId,batch);
            session.setAttribute("pcList", list);
            return ResponseEntity.buildSuccess("登入成功");
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.setCode("991");
            message.setMessage(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.build(Commonconst.LOGIN_FAIL, message);
        }
    }

    @ApiOperation("绑定")
    @PostMapping("/bind")
    public ResponseEntity<?> bind(@RequestBody(required = false) BindInfo userInfo, HttpServletRequest request){
        String openId= RequestUtils.getCookieByName(request, Constants.OPEN_ID);
        logger.info("-------openId-------："+openId);
        String code=userInfo.getInvitationCode();//验证码
        String phone=userInfo.getTelNum();//电话号码
        if (!loginService.check(phone,code)){
           return ResponseEntity.build(400,"验证码错误", null);
        };
        logger.info("-----验证success---");
        try {
            //传人绑定信息,返回信息
            boolean id = loginService.bind(userInfo, openId);//true表示绑定成功
            return ResponseEntity.build(200,"绑定成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            logger.info("-----异常信息---："+e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }

    }

    @ApiOperation("解除绑定")
    @GetMapping("/unbind")
    public ResponseEntity<?> unbind(HttpServletRequest request){
        String openId= RequestUtils.getCookieByName(request, Constants.OPEN_ID);
        if (C.checkNullOrEmpty(openId)) {
            openId=(String)request.getSession().getAttribute("openId");
        }
        try {
            String data = loginService.unBind(openId);
            return ResponseEntity.buildSuccess(data);
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }
    }
    @ApiOperation("获取验证码")
    @GetMapping("/getCode")
    public ResponseEntity<?> getCode(String telNum, HttpServletRequest request) {
        try {
            String status = loginService.getCheckCode(telNum);
            return ResponseEntity.buildSuccess(status);
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }
    }

    @ApiOperation("判断session 是否存在")
    @GetMapping("/isSessionAlive")
    public ResponseEntity<?> isSessionAlive(HttpServletRequest request) {
        if(request.getSession(false)==null){
//            System.out.println("Session has been invalidated!");
            return ResponseEntity.build(200,"Session has been invalidated!", null);
        }
        else{
//            System.out.println("Session is active!");
            return ResponseEntity.build(201, "Session is active!", null);
        }

    }

}
