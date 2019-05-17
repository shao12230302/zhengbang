package com.zb.byb.service;

import com.zb.byb.entity.FeedRecord;
import com.zb.byb.entity.Pigwash;

import java.util.List;

public interface FeedRecordService {
    /**
     * 饲喂添加
     * @param feedRecord 饲喂记录
     * @param userId 养户id
     * @return
     */
    String addFeedRecord(FeedRecord feedRecord, String userId)throws Exception;

    /**
     *饲喂记录
     * @param userId 养户id
     * @return
     *//*
    String queryFeedRecord(String batchId,String userId)throws Exception;*/

    /**
     *  饲喂记录列表
     * @param
     * @param userId 养户id
     * @return
     */
    List<FeedRecord> queryFeedRecordList(String userId,FeedRecord feedRecord) throws Exception;

    /**
     * 根据id查看饲喂记录
     * @param rcordId
     * @return
     * @throws Exception
     */
    FeedRecord queryFeedRecordbyRcordId(String rcordId) throws Exception;
    /**
     *  饲料选择列表
     * @param batchId 养猪批次号
     * @return
     */
    List<Pigwash> pigwashList(String batchId)throws Exception;

    String cancleFeedRecord(String rcordId) throws Exception;
}
