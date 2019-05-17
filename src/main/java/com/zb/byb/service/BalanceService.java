package com.zb.byb.service;

import com.zb.byb.entity.Balance;
import com.zb.byb.entity.BalanceRecord;

import java.util.List;

public interface BalanceService {
    /**
     * 根据批次带出申请结算的信息
     * @param batchId 批次id
     * @param userId 用户id
     * @return info
     * @throws Exception
     */
    Balance initInfoByBatchId(String batchId,String userId)throws Exception;
    /**
     * 结算申请
     * @param balance 结算批次号
     * @param userId 用户id
     * @return
     * @throws Exception
     */
    String balanceApply(Balance balance, String userId) throws Exception;

    /**
     * 结算记录列表
     * @return
     * @throws Exception
     */
    List<BalanceRecord> getBalanceRecord(BalanceRecord balanceRecord, String userId)throws Exception;

    /**
     * 查看结算记录详情
     * @param rcordId 单据id
     * @return
     */
    BalanceRecord viewBalanceRecord(String batchId,String rcordId)throws Exception;

    /**
     * 签名提交
     * @param BalanceRecord 结算记录对象
     * @return
     * @throws Exception
     */
    String singer(BalanceRecord BalanceRecord) throws Exception;

    /**
     * 取消申请
     * @param rcordId 单据ID
     * @return
     * @throws Exception
     */
    String cancelApply(String rcordId) throws  Exception;
}
