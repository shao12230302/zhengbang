package com.zb.byb.service;

import com.zb.byb.entity.DrugApply;
import com.zb.byb.entity.EquipmentApply;
import com.zb.byb.entity.MaterialInfo;

import java.util.List;

/**
 * 领药申请,业务接口
 * 作者：谢李
 */
public interface DrugApplyService{

    /**
     * 投苗数据保存
     * @param info
     * @return
     * @throws Exception
     */
    String saveInfo(DrugApply info) throws Exception;

    /**
     * 根据关键字+批次id进行模糊查询药品列表
     */
    List<MaterialInfo> queryMaterialListByFuzzyKey(MaterialInfo queryInfo) throws Exception;

    /**
     * 根据前台传过来的tokenid，初始化数据
     * @param custId
     * @return 返回对象
     * @throws Exception 异常
     */
    DrugApply queryListInitData(String custId) throws Exception;

    /**
     * 根据用户id查询到投苗记录
     * @param custId
     * @return 返回对象
     * @throws Exception 异常
     */
    List<DrugApply> queryInfoRecordList(String custId,DrugApply drugApply) throws Exception;

    /**
     * 根据id查询对象信息
     * @param recordId
     * @return
     * @throws Exception
     */
    DrugApply queryInfoById(String recordId) throws Exception;

    /**
     * 根据recordId删除对象信息
     * @param recordId
     * @return
     * @throws Exception
     */
    boolean deleteInfoById(String recordId) throws Exception;

    /**
     * 签名提交
     * @param drugApply
     * @return
     * @throws Exception
     */
    String singer(DrugApply drugApply) throws Exception;

}
