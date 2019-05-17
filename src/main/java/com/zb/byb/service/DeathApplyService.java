package com.zb.byb.service;

import com.zb.byb.entity.Death;
import com.zb.byb.entity.DeathApply;

import java.util.List;

public interface DeathApplyService {
    /**
     *
     * @param deathApply 死亡申报单
     * @param userId 用户id
     * @return
     */
    String deathApply(DeathApply deathApply, String userId) throws Exception;

    /**
     * 死亡申报记录列表
     * @param userId
     * @return
     */
    List<DeathApply> getDeathApplyRecord(String userId, DeathApply deathApply)throws  Exception;

    /**
     * 查看单条申报记录
     * @param rcord
     * @return
     * @throws Exception
     */
    Death getDeathApplyRecordbyId(String rcord)throws  Exception;

    /**
     * 取消死亡申报
     * @param rcordId
     * @return
     * @throws Exception
     */
    String cancleDeathApply(String rcordId) throws Exception;

    String signerDeathApply(DeathApply deathApply)throws Exception;

}
