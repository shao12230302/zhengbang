package com.zb.byb.service;

import com.zb.byb.entity.YuE;

public interface YuEService {
    /**
     * 余额查询
     * @param userId  养户id
     * @return
     */
    String queryYuE(YuE yuE,String userId) throws Exception;
}
