package com.zb.byb.service;

import com.zb.byb.entity.MyInfo;

public interface MyInfoService {
    /**
     * 查看我的信息
     * @return
     */
    String viewMyInfo(String openId) throws Exception;

}
