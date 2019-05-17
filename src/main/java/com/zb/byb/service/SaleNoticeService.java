package com.zb.byb.service;

import com.zb.byb.entity.SaleNotice;
import java.util.List;

/**
 * @Author: shaoys
 * @Mail: 415992946@qq.com
 * @Date: Created in 10:02 2019/5/5
 **/
public interface SaleNoticeService {
    /**
     * 销售通知信息
     * @param id
     * @return
     * @throws Exception
     */
    List<SaleNotice> getSaleRecordList(String id) throws  Exception;

    SaleNotice getQuerySale (String id) throws Exception;


}
