package com.zb.byb.service;

import com.zb.byb.entity.ReceivedRecord;

import java.util.List;

public interface ReceivedRecordService {
    /**
     * 领用记录查询
     * @param
     *
     * @param receivedRecord 查询条件对象
     * @return
     * @throws Exception
     */
    ReceivedRecord getReceivedList(ReceivedRecord receivedRecord)throws Exception;
}
