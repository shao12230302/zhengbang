package com.zb.byb.service;

import com.zb.byb.entity.Driver;
import com.zb.byb.entity.FeedApply;
import com.zb.byb.entity.LiLiaoInfo;

import java.util.List;

public interface FeedApplyService {
    /**
     *
     * @param feedApply 领料申请
     * @param userId  用户id
     * @return
     * @throws Exception
     */
    String feedApply(FeedApply feedApply,String userId) throws Exception;

    /**
     *
     * @param feedApply 领料申请
     * @param userId 用户id
     * @return
     * @throws Exception
     */
    List<FeedApply> queryFeedApply(FeedApply feedApply, String userId) throws Exception;

    /**
     * 查看领料详情
     * @param rcordId
     * @return
     * @throws Exception
     */
    FeedApply viewFeedApply(String rcordId)throws Exception;

    /**
     * 签名提交
     * @param feedApply
     * @return
     * @throws Exception
     */
    String singer(FeedApply feedApply) throws Exception;

    /**
     * 取消领料申请
     * @param rcordId
     * @return
     * @throws Exception
     */
    String cancleFeedApply(String rcordId)throws Exception;

    /**
     * 查询饲料信息列表
     * @param feedApply
     * @return
     * @throws Exception
     */
    List<LiLiaoInfo> getFeedList(FeedApply feedApply)throws Exception;

    /**
     * 查询拉料司机列表
     * @param custId
     * @return
     * @throws Exception
     */
    List<Driver> getDriverList(FeedApply feedApply,String custId)throws Exception;
}
