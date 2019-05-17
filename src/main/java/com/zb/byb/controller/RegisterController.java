package com.zb.byb.controller;

import com.zb.byb.common.C;
import com.zb.byb.common.Commonconst;
import com.zb.byb.common.Constants;

import com.zb.byb.entity.Introducer;
import com.zb.byb.entity.ServiceDept;
import com.zb.byb.entity.UserInfo;
import com.zb.byb.service.LoginService;
import com.zb.byb.util.RequestUtils;
import com.zb.framework.common.entity.Message;
import com.zb.framework.common.entity.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 注册信息
 */
@RestController
@RequestMapping("/api/register")
public class RegisterController {
    @Autowired
    private LoginService loginService;
    @ApiOperation("保存注册信息")
    @PostMapping("/save")
    public ResponseEntity<?> register(@RequestBody UserInfo userInfo, HttpServletRequest request) {
        String openId = RequestUtils.getCookieByName(request, Constants.OPEN_ID);
        String phone=userInfo.getTelNum();
        String code=userInfo.getInvitationCode();
        //openId="12345687";
        if (!loginService.check(phone,code)){
            return ResponseEntity.build(100,"验证码错误", null);
        };
        try {
            if(userInfo.getIdno()==null || userInfo.getIdno().length()==0){
                throw new Exception("身份证号不能为空");
            }
            if(userInfo.getCustName()==null || userInfo.getCustName().length()==0){
                throw new Exception("名字不能为空");
            }
            String backData=loginService.register(userInfo,openId);
            return ResponseEntity.buildSuccess(backData);
        } catch (Exception e) {
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }

    }


    @ApiOperation("介绍人获取")
    @GetMapping("/getInstroduce")
    public ResponseEntity<List<Introducer>> getInstroduce(String name){
        Introducer introducer=new Introducer();
        introducer.setName(name);
        try {
            List<Introducer> list=loginService.getIntroducer(introducer);
            for (int i=0;i<list.size();i++){
                String idno=list.get(i).getIdno();
                list.get(i).setIdno(subIdno(idno));
            }
            return ResponseEntity.buildSuccess(list);
        }catch (Exception e){
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }
    }

    /**
     * 截取身份证号码
     * @return
     */
    private String subIdno(String idno){
        String idnoShow="";
        if(idno.trim().length()==18){
            idnoShow=idno.substring(1,5)+"***"+idno.substring(12,18);
        }else {
            idnoShow =idno.substring(1,5)+"***"+idno.substring(9,15);
        }
        return idnoShow;
    }

    @ApiOperation("获取服务部列表")
    @GetMapping("/getDept")
    public ResponseEntity<?> getServiceDept(Integer pageNumber,Integer pageSize,String name, HttpServletRequest request) {
        ServiceDept serviceDept=new ServiceDept();
        serviceDept.setPageNumber(pageNumber);
        serviceDept.setPageSize(pageSize);
        serviceDept.setName(name);
        try {
            List<ServiceDept> deptList = loginService.getServiceDept(serviceDept);
            return ResponseEntity.buildSuccess(deptList);
        } catch (Exception e) {
            Message message = new Message();
            message.setCode(C.parseStr(Commonconst.FailStatus));
            message.setMessage(e.getMessage());
            return ResponseEntity.build(Commonconst.FailStatus, message);
        }

    }
}
