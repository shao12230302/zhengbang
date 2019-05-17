package com.zb.byb.service;

import com.zb.byb.entity.EntrustInfo;
import com.zb.byb.entity.Equipment;
import com.zb.byb.entity.EquipmentApply;
import com.zb.byb.entity.FileEntry;

import java.util.List;

/**
 * 设备领用申请,业务接口
 * 作者：谢李
 */
public interface EquipmentApplyService {

    /**
     * 设备领用申请数据保存
     * @param info
     * @return
     * @throws Exception
     */
    String saveInfo(EquipmentApply info) throws Exception;

    /**
     * 查询委托人列表
     * @param info
     * @return
     * @throws Exception
     */
    List<EntrustInfo> queryEntrustList(EquipmentApply info) throws Exception;

    /**
     * 根据前台传过来的tokenid，初始化数据
     * @param tokenId
     * @return 返回对象
     * @throws Exception 异常
     */
    EquipmentApply queryListInitData(String tokenId) throws Exception;

    /**
     * 根据用户id查询到设备领用申请记录
     * @param queryInfo
     * @return 返回对象
     * @throws Exception 异常
     */
    List<EquipmentApply> queryInfoRecordList(EquipmentApply queryInfo) throws Exception;

    /**
     * 根据id查询对象信息
     * @param id
     * @return
     * @throws Exception
     */
    EquipmentApply queryInfoById(String id) throws Exception;

    /**
     * 删除信息通过id
     * @param id
     * @return
     * @throws Exception
     */
    String deleteInfoById(String id) throws Exception;

    /**
     * 设备查询列表
     * @param keyword
     * @return
     * @throws Exception
     */
    List<Equipment> searchEquipment(String keyword,String custId)throws Exception;

    String signerEquipApply(EquipmentApply equipmentApply) throws Exception;

}
