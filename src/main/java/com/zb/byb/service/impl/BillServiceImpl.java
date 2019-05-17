package com.zb.byb.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zb.byb.common.C;
import com.zb.byb.common.Commonconst;
import com.zb.byb.entity.*;
import com.zb.byb.service.BillService;
import com.zb.byb.util.BackTransmitUtil;
import com.zb.byb.util.HtmlToImageUtil;
import com.zb.byb.util.JsonPluginsUtil;
import com.zb.byb.util.MethodName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @Function: 对账单列表和详情
* @Author: shaoys
* @Date: Created in 1:21 2019/5/12
**/
@Service
public class BillServiceImpl implements BillService {
    @Autowired
    private BackTransmitUtil backTransmitUtil;
    @Override
    public List queryInfoRecordList(BillInfo info) throws Exception {

        if (C.checkNullOrEmpty(info.getCustId()))
            throw new Exception("未传入养户id");

        Map<String, Object> map = new HashMap<>();
        map.put("custId", info.getCustId());
        map.put("source", Commonconst.WX_Flag);
        map.put("data", info);

        // 要传入数据进行转化
        String data = JSONObject.toJSONString(map);
        String jsonData = backTransmitUtil.invokeFunc(data, MethodName.Method_Name_checkBill);
        return JsonPluginsUtil.jsonToBeanList(jsonData, BillInfo.class);
    }

    @Override
    public String queryBillRecordById(BillInfo info) throws Exception {
        if (C.checkNullOrEmpty(info.getCustId()))
            throw new Exception("未传入记录id");
        Map<String, Object> map = new HashMap<>();
        map.put("source", Commonconst.WX_Flag);
        map.put("data", info);
        String data = JSONObject.toJSONString(map);
        String jsonData = backTransmitUtil.invokeFunc(data, MethodName.Method_Name_checkBill);
        List<BillInfo> list = this.queryInfoRecordList(info);
        for (BillInfo bill : list) {

            //  如果记录为空，或者记录id不一致
            if (C.checkNullOrEmpty(bill.getRcordId()) || !bill.getRcordId().equals(info.getRcordId()))
                continue;

            return generateHtmlTempStr(bill);
        }

        return null;
    }

    /**
     * 生成hmtlStr,动态拼接数据
     *
     * @param info BillInfo
     * @return htmlTemplate
     */
    private String generateHtmlTempStr(BillInfo info) throws Exception {
        if (info == null)
            throw new Exception("无数据");

        // 头部信息
        StringBuilder templateStr = new StringBuilder("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                "    <tile>\n" +
                "        </title>\n" +
                "        <style type=\"text/css\">\n" +
                "            .tftable {\n" +
                "                font-size: 12px;\n" +
                "                color: #000;\n" +
                "                border-width: 1px;\n" +
                "                border-color: #686767;\n" +
                "                border-collapse: collapse;\n" +
                "                text-align: center;\n" +
                "            }\n" +
                "\n" +
                "            .tl {\n" +
                "                text-align: left;\n" +
                "            }\n" +
                "\n" +
                "\n" +
                "            .tftable th {\n" +
                "                font-size: 12px;\n" +
                "                background-color: #171515;\n" +
                "                border-width: 1px;\n" +
                "                padding: 8px;\n" +
                "                border-style: solid;\n" +
                "                border-color: #686767;\n" +
                "                text-align: left;\n" +
                "            }\n" +
                "\n" +
                "            .tftable tr {\n" +
                "                background-color: #ffffff;\n" +
                "            }\n" +
                "\n" +
                "            .tftable td {\n" +
                "                font-size: 12px;\n" +
                "                border-width: 1px;\n" +
                "                padding: 8px;\n" +
                "                border-style: solid;\n" +
                "                border-color: #686767;\n" +
                "            }\n" +
                "\n" +
                "            .bg {\n" +
                "                background-color: #dddddd !important;\n" +
                "            }\n" +
                "        </style>\n" +
                "</head>\n" +
                "\n");

        // body
        templateStr.append("<body>\n<table class=\"tftable\" border=\"1\">\n");
        // 列头部分--养户信息
        templateStr.append("<tr>\n" +
                "            <td colspan=\"11\">"+info.getDepartment()+"养户月度对账单</td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td colspan=\"1\">养户姓名</td>\n" +
                "            <td colspan=\"2\">"+info.getCustName()+"</td>\n" +
                "            <td colspan=\"1\">批次名称</td>\n" +
                "            <td colspan=\"2\">"+info.getBatchName()+"</td>\n" +
                "            <td colspan=\"1\">期间</td>\n" +
                "            <td colspan=\"2\">"+info.getPeriod()+"</td>\n" +
                "            <td colspan=\"2\"></td>\n" +
                "        </tr>\n");
        // 猪苗领用---详情部分
        templateStr.append(
                "        <tr class=\"bg\">\n" +
                "            <td colspan=\"11\">猪苗领用</td>\n" +
                "        </tr>\n" +
                "        <tr class=\"bg\">\n" +
                "            <td>投苗日期</td>\n" +
                "            <td>头数</td>\n" +
                "            <td>品种</td>\n" +
                "            <td>投苗日龄</td>\n" +
                "            <td>均重(kg)</td>\n" +
                "            <td>重量(kg)</td>\n" +
                "            <td>基础重\n" +
                "                （kg）</td>\n" +
                "            <td>基础价\n" +
                "                （元/头</td>\n" +
                "            <td>超重价\n" +
                "                （元/kg）</td>\n" +
                "            <td>低重价\n" +
                "                （元/kg）</td>\n" +
                "            <td>结算金额\n" +
                "                （元)</td>\n" +
                "        </tr>\n" );
        templateStr.append(this.zmlyData(info));
//        templateStr.append(this.userHasConfirm());

        // 饲料领用--详情部分
        templateStr.append(
                "        <tr class=\"bg\">\n" +
                "            <td colspan=\"11\">饲料领用</td>\n" +
                "        </tr>\n" +
                "        <tr class=\"bg\">\n" +
                "            <td>领用日期</td>\n" +
                "            <td colspan=\"2\">品名</td>\n" +
                "            <td>规格\n" +
                "                （kg/包）</td>\n" +
                "            <td>数量\n" +
                "                （包）</td>\n" +
                "            <td>单价\n" +
                "                （元/包）</td>\n" +
                "            <td>数量（kg）</td>\n" +
                "            <td>金额（元）</td>\n" +
                "            <td>是否本人领取</td>\n" +
                "            <td>委托司机</td>\n" +
                "            <td>司机车牌</td>\n" +
                "        </tr>\n" );
        templateStr.append(this.swlyData(info));
//        templateStr.append(this.userHasConfirm());


        // 设备领用--详情部分
        templateStr.append(
                "        <tr class=\"bg\">\n" +
                "            <td colspan=\"11\">设备领用</td>\n" +
                "        </tr>\n" +
                "        <tr class=\"bg\">\n" +
                "            <td>领用日期</td>\n" +
                "            <td colspan=\"2\">品名</td>\n" +
                "            <td colspan=\"2\">规格</td>\n" +
                "            <td>单位</td>\n" +
                "            <td>数量</td>\n" +
                "            <td>结算单价</td>\n" +
                "            <td>结算金额（元）</td>\n" +
                "            <td>是否本人领取</td>\n" +
                "            <td>委托领取人</td>\n" +
                "        </tr>\n" );
        templateStr.append(this.sblyData(info));
//        templateStr.append(this.userHasConfirm());

        // 兽药领用--详情部分
        templateStr.append(
                "        <tr class=\"bg\">\n" +
                "            <td colspan=\"11\">兽药领用</td>\n" +
                "        </tr>\n" +
                "        <tr class=\"bg\">\n" +
                "            <td>领用日期</td>\n" +
                "            <td colspan=\"2\">品名</td>\n" +
                "            <td colspan=\"2\">规格</td>\n" +
                "            <td>单位</td>\n" +
                "            <td>数量</td>\n" +
                "            <td>结算单价</td>\n" +
                "            <td>结算金额（元）</td>\n" +
                "            <td>是否本人领取</td>\n" +
                "            <td>委托领取人</td>\n" +
                "        </tr>\n" );
        templateStr.append(this.sylyData(info));
//        templateStr.append(this.userHasConfirm());

        templateStr.append("    </table>\n" + "</body>\n" + "\n" + "</html>" );
        return templateStr.toString();
    }

    /**
     * 设备领用
     */
    private String sblyData(BillInfo info) {
        if (info == null || info.getEquipEntry().size() <= 0)
            return "";

        List<EquipEntry> list = info.getEquipEntry();
        StringBuilder result = new StringBuilder();
        for (EquipEntry entry : list) {
            if (entry == null)
                continue;

            // 领用日期	品名	 规格	 单位	 数量	 结算单价	结算金额（元）	是否本人领取	委托领取人
            result.append(  "        <tr>\n" +
                            "            <td>"+entry.getApplyDate()+"</td>\n" +
                            "            <td colspan=\"2\">"+entry.getMaterialName()+"</td>\n" +
                            "            <td colspan=\"2\">"+(entry.getModel()!=null?entry.getModel():"无")+"</td>\n" +
                            "            <td>"+entry.getUnit()+"</td>\n" +
                            "            <td>"+entry.getQty()+"</td>\n" +
                            "            <td>"+entry.getPrice()+"</td>\n" +
                            "            <td>"+entry.getAmount()+"</td>\n" +
                            "            <td>"+(entry.getIsSelf()!=null && entry.getIsSelf().equals("1")?"是":"否")+"</td>\n" +
                            "            <td>"+(entry.getEntrustName()!=null?entry.getEntrustName():"无")+"</td>\n" +
                            "        </tr>\n");
        }

        return result.toString();
    }

    /**
     * 兽药领用数据
     */
    private String sylyData(BillInfo info) {
        if (info == null || info.getDrugEntry().size() <= 0)
            return "";

        List<DrugEntry> list = info.getDrugEntry();
        StringBuilder result = new StringBuilder();
        for (DrugEntry entry : list) {
            if (entry == null)
                continue;

            // 领用日期	品名	规格	 单位	数量	 结算单价	结算金额（元）	是否本人领取	委托领取人
            result.append(  "        <tr>\n" +
                            "            <td>"+entry.getApplyDate()+"</td>\n" +
                            "            <td colspan=\"2\">"+entry.getMaterialName()+"</td>\n" +
                            "            <td colspan=\"2\">"+(entry.getModel()!=null?entry.getModel():"无")+"</td>\n" +
                            "            <td>"+(entry.getUnit()!=null?entry.getUnit():"无")+"</td>\n" +
                            "            <td>"+entry.getQty()+"</td>\n" +
                            "            <td>"+entry.getPrice()+"</td>\n" +
                            "            <td>"+entry.getAmount()+"</td>\n" +
                            "            <td>"+(entry.getIsSelf()!=null && entry.getIsSelf().equals("1")?"是":"否")+"</td>\n" +
                            "            <td>"+(entry.getEntrustName()!=null?entry.getEntrustName():"无")+"</td>\n" +
                            "        </tr>\n");
        }

        return result.toString();
    }

    /**
     * 饲料领用数据
     */
    private String swlyData(BillInfo info) {
        if (info == null || info.getFeedEntry().size() <= 0)
            return "";

        List<FeedEntry> list = info.getFeedEntry();
        StringBuilder result = new StringBuilder();
        for (FeedEntry entry : list) {
            if (entry == null)
                continue;
            // 领用日期	品名	规格 （kg/包）	数量 （包）	单价 （元/包）	数量（kg）	金额（元）	是否本人领取	委托司机	司机车牌
            result.append(  "        <tr>\n" +
                            "            <td>"+entry.getApplyDate()+"</td>\n" +
                            "            <td colspan=\"2\">"+entry.getMaterialName()+"</td>\n" +
                            "            <td>"+(entry.getModel()!=null?entry.getModel():"无")+"</td>\n" +
                            "            <td>"+entry.getQty()+"</td>\n" +
                            "            <td>"+entry.getPrice()+"</td>\n" +
                            "            <td>"+entry.getBaseQty()+"</td>\n" +
                            "            <td>"+entry.getAmount()+"</td>\n" +
                            "            <td>"+(entry.getIsSelf()!=null && entry.getIsSelf().equals("1")?"是":"否")+"</td>\n" +
                            "            <td>"+(entry.getDriverName()!=null?entry.getMaterialName():"无")+"</td>\n" +
                            "            <td>"+(entry.getDriverCarNo()!=null?entry.getDriverCarNo():"无")+"</td>\n" +
                            "        </tr>\n");
        }
        return result.toString();
    }

    /**
     * 猪苗领用数据
      */
    private String zmlyData(BillInfo info) {
        if (info == null || info.getPigEntry().size() <= 0)
            return "";

        List<PigEntry> list = info.getPigEntry();
        StringBuilder result = new StringBuilder();
        for (PigEntry pigEntry : list) {
            if (pigEntry == null)
                continue;

            // 投苗日期	头数	 品种	投苗日龄	均重(kg)	重量(kg)	基础重 （kg）	基础价 （元/头	超重价 （元/kg）	低重价 （元/kg）	结算金额 （元)
            result.append(  "        <tr>\n" +
                            "            <td>"+pigEntry.getPigingDate()+"</td>\n" +
                            "            <td>"+pigEntry.getQty()+"</td>\n" +
                            "            <td>"+(pigEntry.getMaterialName()!=null?pigEntry.getMaterialName():"无")+"</td>\n" +
                            "            <td>"+pigEntry.getDayAge()+"</td>\n" +
                            "            <td>"+pigEntry.getAvgWeight()+"</td>\n" +
                            "            <td>"+pigEntry.getWeight()+"</td>\n" +
                            "            <td>"+pigEntry.getBaseWeight()+"</td>\n" +
                            "            <td>"+pigEntry.getBasePrice()+"</td>\n" +
                            "            <td>"+pigEntry.getSurpPrice()+"</td>\n" +
                            "            <td>"+pigEntry.getInsPrice()+"</td>\n" +
                            "            <td>"+pigEntry.getAmmount()+"</td>\n" +
                            "        </tr>\n");
        }
        return result.toString();
    }

    /**
     * 以上数据本人已确认
     * @return
     */
    private String userHasConfirm()
    {
        return  " <tr>\n<td colspan=\"11\" class=\"tl\">以上数据本人已确认：</td>\n</tr>\n";
    }

    private void generatePicture() {
        String htmlTemplate = "    <table border=\"1\" width=\"60%\" bgcolor=\"#e9faff\" cellpadding=\"2\">\r\n" +
                "        <caption>课程表</caption>\r\n" +
                "        <tr align=\"center\">\r\n" +
                "            <td colspan=\"2\">时间\\日期</td>\r\n" +
                "            <td>一</td>\r\n" +
                "            <td>二</td>\r\n" +
                "            <td>三</td>\r\n" +
                "            <td>四</td>\r\n" +
                "            <td>五</td>\r\n" +
                "        </tr>\r\n" +
                "\r\n" +
                "        <tr align=\"center\">\r\n" +
                "            <td rowspan=\"2\">上午</td>\r\n" +
                "            <td>9:30-10:15</td>\r\n" +
                "            <td>语文</td>\r\n" +
                "            <td>语文</td>\r\n" +
                "            <td>语文</td>\r\n" +
                "            <td>语文</td>\r\n" +
                "            <td>语文</td>\r\n" +
                "        </tr>\r\n" +
                "\r\n" +
                "        <tr align=\"center\">\r\n" +
                "            <td>10:25-11:10</td>\r\n" +
                "            <td>语文</td>\r\n" +
                "            <td>语文</td>\r\n" +
                "            <td>语文</td>\r\n" +
                "            <td>语文</td>\r\n" +
                "            <td>语文</td>\r\n" +
                "        </tr>\r\n" +
                "\r\n" +
                "        <tr>\r\n" +
                "            <td colspan=\"7\">&nbsp;</td>\r\n" +
                "        </tr>\r\n" +
                "\r\n" +
                "        <tr align=\"center\">\r\n" +
                "            <td rowspan=\"2\">下午</td>\r\n" +
                "            <td>14:30-15:15</td>\r\n" +
                "            <td>语文</td>\r\n" +
                "            <td>语文</td>\r\n" +
                "            <td>语文</td>\r\n" +
                "            <td>语文</td>\r\n" +
                "            <td>语文</td>\r\n" +
                "        </tr>\r\n" +
                "\r\n" +
                "        <tr align=\"center\">\r\n" +
                "            <td>15:25-16:10</td>\r\n" +
                "            <td>语文</td>\r\n" +
                "            <td>语文</td>\r\n" +
                "            <td>语文</td>\r\n" +
                "            <td>语文</td>\r\n" +
                "            <td>语文</td>\r\n" +
                "        </tr>\r\n" +
                "    </table>";
//        System.out.println(htmlTemplate);
        byte[] bytes = new byte[0];
        try {
            bytes = HtmlToImageUtil.html2png(Color.white, htmlTemplate, new EmptyBorder(0, 0, 0, 0), HtmlToImageUtil.Width, HtmlToImageUtil.Height);
            String filePath = Commonconst.TempPath + C.newGuid() + ".png";
            OutputStream out = new FileOutputStream(new File(filePath));
            out.write(bytes);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
