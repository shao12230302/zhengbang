package com.zb.byb.service;

import com.zb.byb.entity.DataRecord;
import com.zb.byb.entity.TouMiao;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 投苗业务层处理
 * 作者：谢李
 */
public interface TouMiaoService
{
    /**
     * 通过登入人id获取投苗
     * @param userId
     * @return
     * @throws Exception
     */
    String queryListByUser(String userId) throws Exception;

    /**
     * 投苗数据保存
     * @param info
     * @return
     * @throws Exception
     */
    String saveInfo(TouMiao info) throws Exception;

    /**
     * 根据前台传过来的tokenid，初始化数据
     * @param custId
     * @return custId
     * @throws Exception 异常
     */
    TouMiao queryListInitData(String custId) throws Exception;

    /**
     * 根据前台tmid,查询到详细信息
     * @param tmid
     * @return 返回对象
     * @throws Exception 异常
     */
    TouMiao queryInfoById(String tmid) throws Exception;

    /**
     * 根据用户id查询到投苗记录
     * @param custId
     * @return 返回对象
     * @throws Exception 异常
     */
    List<TouMiao> queryInfoRecordList(String custId, TouMiao info) throws Exception;

    /**
     * 取消偷瞄申请
     * @param touMiao
     * @return
     * @throws Exception
     */
    String singerTouMiao(TouMiao touMiao) throws Exception;

    /**
     * 取消投苗申请
     * @param touMiao
     * @return
     * @throws Exception
     */
    String cancleTouMiao(String rcordId) throws Exception;
}
