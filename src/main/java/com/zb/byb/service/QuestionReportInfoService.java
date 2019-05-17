package com.zb.byb.service;

import com.zb.byb.entity.NormalQuestionInfo;
import com.zb.byb.entity.QuestionReportInfo;

import java.util.List;
import java.util.Map;

/**
 * 作者：谢李
 */
public interface QuestionReportInfoService
{
    /**
     * 保存反馈信息
     * @param info 问题反馈
     * @throws Exception 异常
     */
    String saveQuestionReport(QuestionReportInfo info)throws Exception;

    /**
     * 根据参数,进行常见问题获取
     * @param
     * @return
     * @throws Exception
     */
    List<QuestionReportInfo> queryNormalQuestionList(String id) throws Exception;

    /**
     * 通过问题id查询问题信息信息
     * @param id
     * @return
     * @throws Exception
     */
    QuestionReportInfo queryQuestionInfoById (String id) throws Exception;

    /**
     * 根据id删除问题信息
     * @param id id
     * @return
     * @throws Exception
     */
    boolean deleteQuestionInfoById(String id) throws Exception;
}
