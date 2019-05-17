package com.zb.byb.util;

import com.alibaba.fastjson.JSON;
import com.zb.byb.common.C;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Json与javaBean之间的转换工具类
 * 作者：谢李
 */
public class JsonPluginsUtil
{
    // 获取数据标识
    public static final String Data = "data";
    // 返回编码
    public static final String Code = "code";
    // 返回id
    public static final String Id = "id";

    /**
     * 从一个JSON 对象字符格式中得到一个java对象
     *
     * @param jsonString
     * @param beanCalss
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T jsonToBean(String jsonString, Class<T> beanCalss)throws Exception {
        String data = getData(jsonString);//处理数据
        if (C.checkNullOrEmpty(data))
            return null;

        JSONObject beanJson = JSONObject.fromObject(data);
//        T bean = (T) JSONObject.toBean(beanJson, beanCalss);

        T bean = (T) JSON.parseObject(data, beanCalss);
        return bean;

    }

    /**
     * 将java对象转换成json字符串
     *
     * @param bean
     * @return
     */
    public static String beanToJson(Object bean) {

        JSONObject json = JSONObject.fromObject(bean);

        return json.toString();

    }

    /**
     * 将java对象转换成json字符串
     *
     * @param bean
     * @return
     */
    public static String beanToJson(Object bean, String[] _nory_changes, boolean nory) {

        JSONObject json = null;

        if(nory){//转换_nory_changes里的属性

            Field[] fields = bean.getClass().getDeclaredFields();
            String str = "";
            for(Field field : fields){
//              System.out.println(field.getName());
                str+=(":"+field.getName());
            }
            fields = bean.getClass().getSuperclass().getDeclaredFields();
            for(Field field : fields){
//              System.out.println(field.getName());
                str+=(":"+field.getName());
            }
            str+=":";
            for(String s : _nory_changes){
                str = str.replace(":"+s+":", ":");
            }
            json = JSONObject.fromObject(bean,configJson(str.split(":")));

        }else{//转换除了_nory_changes里的属性



            json = JSONObject.fromObject(bean,configJson(_nory_changes));
        }



        return json.toString();

    }
    private static JsonConfig configJson(String[] excludes) {

        JsonConfig jsonConfig = new JsonConfig();

        jsonConfig.setExcludes(excludes);
//
        jsonConfig.setIgnoreDefaultExcludes(false);
//
//              jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

//              jsonConfig.registerJsonValueProcessor(Date.class,
//
//                  new DateJsonValueProcessor(datePattern));



        return jsonConfig;

    }





    /**
     * 将java对象List集合转换成json字符串
     * @param beans
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String beanListToJson(List beans) {

        StringBuffer rest = new StringBuffer();

        rest.append("[");

        int size = beans.size();

        for (int i = 0; i < size; i++) {

            rest.append(beanToJson(beans.get(i))+((i<size-1)?",":""));

        }

        rest.append("]");

        return rest.toString();

    }

    /**
     *
     * @param beans
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String beanListToJson(List beans, String[] _nory_changes, boolean nory) {

        StringBuffer rest = new StringBuffer();

        rest.append("[");

        int size = beans.size();

        for (int i = 0; i < size; i++) {
            try{
                rest.append(beanToJson(beans.get(i),_nory_changes,nory));
                if(i<size-1){
                    rest.append(",");
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        rest.append("]");

        return rest.toString();

    }

    /**
     * 从json HASH表达式中获取一个map，改map支持嵌套功能
     *
     * @param jsonString
     * @return
     */
    @SuppressWarnings({ "unchecked" })
    public static Map jsonToMap(String jsonString) {
        if (C.checkNullOrEmpty(jsonString))
            return new HashMap();

        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        String obj = jsonObject.getString(Data);
        if (C.checkNullOrEmpty(obj))
            return new HashMap();

        JSONObject mapJson = JSONObject.fromObject(obj);


        JSONObject jsonMap = JSONObject.fromObject(mapJson);
        Iterator keyIter = jsonMap.keys();
        String key;
        Object value;
        Map valueMap = new HashMap();
        while (keyIter.hasNext()) {
            key = (String) keyIter.next();
            value = jsonMap.get(key).toString();
            valueMap.put(key, value);
        }

        return valueMap;
    }

    /**
     * map集合转换成json格式数据
     * @param map
     * @return
     */
    public static String mapToJson(Map<String, ?> map, String[] _nory_changes, boolean nory){

        String s_json = "{";

        Set<String> key = map.keySet();
        for (Iterator<?> it = key.iterator(); it.hasNext();) {
            String s = (String) it.next();
            if(map.get(s) == null){

            }else if(map.get(s) instanceof List<?>){
                s_json+=(s+":"+JsonPluginsUtil.beanListToJson((List<?>)map.get(s), _nory_changes, nory));

            }else{
                JSONObject json = JSONObject.fromObject(map);
                s_json += (s+":"+json.toString());;
            }

            if(it.hasNext()){
                s_json+=",";
            }
        }

        s_json+="}";
        return s_json;
    }

    /**
     * 从json数组中得到相应java数组
     *
     * @param jsonString
     * @return
     */
    public static Object[] jsonToObjectArray(String jsonString) {

        JSONArray jsonArray = JSONArray.fromObject(jsonString);

        return jsonArray.toArray();

    }

    public static String listToJson(List<?> list) {

        JSONArray jsonArray = JSONArray.fromObject(list);

        return jsonArray.toString();

    }

    /**
     * 从json对象集合表达式中得到一个java对象列表
     *
     * @param jsonString
     * @param beanClass
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> jsonToBeanList(String jsonString, Class<T> beanClass)throws Exception {
        if (beanClass == null || C.checkNullOrEmpty(jsonString))
            return new ArrayList<>();
        JSONArray listdata=getListData(jsonString);
        List<T> list =com.alibaba.fastjson.JSONArray.parseArray(listdata.toString(),beanClass);
        return list;

    }

    /**
     * cqp
     * @param jsonStr
     * @param s
     * @param <T>
     * @return
     */
    public static <T>List<T> jsonTOList(String jsonStr,Class<T> s)throws Exception{

        JSONArray listdata = getListData(jsonStr);//处理数据
        if (C.checkNullOrEmpty(listdata))
            return null;
        List<T> list =com.alibaba.fastjson.JSONArray.parseArray(listdata.toString(),s);
        return list;
    }

    /**
     * 从json数组中解析出java字符串数组
     *
     * @param jsonString
     * @return
     */
    public static String[] jsonToStringArray(String jsonString) {

        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        String[] stringArray = new String[jsonArray.size()];
        int size = jsonArray.size();

        for (int i = 0; i < size; i++) {

            stringArray[i] = jsonArray.getString(i);

        }

        return stringArray;
    }

    /**
     * 从json数组中解析出javaLong型对象数组
     *
     * @param jsonString
     * @return
     */
    public static Long[] jsonToLongArray(String jsonString) {

        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        int size = jsonArray.size();
        Long[] longArray = new Long[size];

        for (int i = 0; i < size; i++) {

            longArray[i] = jsonArray.getLong(i);

        }

        return longArray;

    }

    /**
     * 从json数组中解析出java Integer型对象数组
     *
     * @param jsonString
     * @return
     */
    public static Integer[] jsonToIntegerArray(String jsonString) {

        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        int size = jsonArray.size();
        Integer[] integerArray = new Integer[size];

        for (int i = 0; i < size; i++) {

            integerArray[i] = jsonArray.getInt(i);

        }

        return integerArray;

    }

    /**
     * 从json数组中解析出java Double型对象数组
     *
     * @param jsonString
     * @return
     */
    public static Double[] jsonToDoubleArray(String jsonString) {

        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        int size = jsonArray.size();
        Double[] doubleArray = new Double[size];

        for (int i = 0; i < size; i++) {

            doubleArray[i] = jsonArray.getDouble(i);

        }

        return doubleArray;

    }

    /**
     * json数据通过Data获取数据
     * @param jsonStr
     * @return
     */
    public static String getSuccessData(String jsonStr)
    {
        if (C.checkNullOrEmpty(jsonStr))
            return null;

        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        return C.parseStr(jsonObject.getString(Data ));
    }

    /**
     * json数据通过Data获取数据
     * @param jsonStr
     * @return
     */
    public static String getSuccessData(String jsonStr, String key)
    {
        if (C.checkNullOrEmpty(jsonStr))
            return null;

        if (C.checkNullOrEmpty(key))
            key = Data;

        com.alibaba.fastjson.JSONObject jsonObj = JSON.parseObject(jsonStr);
        return jsonObj.getString(key);
    }


    /**
     * 判断是否返回成功
     * @param jsonString
     * @return
     */
    public static boolean isRequestSuccess(String jsonString)
    {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        String code = C.parseStr(jsonObject.getString(Code ));
        if (C.checkNullOrEmpty(code))
            return false;

        if (code.equalsIgnoreCase("0000"))
            return true;

        return false;
    }

    /**
     * 判断是否返回成功
     * @param jsonString
     * @return
     */
    public static String isRequestSuccessBackId(String jsonString)throws Exception
    {
        if (C.checkNullOrEmpty(jsonString))
            return null;
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        if(!"0000".equals(jsonObject.getString("code")))
            throw new Exception("保存失败："+((jsonObject.has("msg"))?jsonObject.getString("msg"):"保存信息未写全"));
        String id = C.parseStr(jsonObject.getString(Id ));
        if (C.checkNullOrEmpty(Id))
            return "";
        return id;
    }

    private static String getData(String jsonString)throws Exception{
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        if(!"0000".equals(jsonObject.getString("code"))){
            throw new Exception("查询失败："+((jsonObject.has("msg"))?jsonObject.getString("msg"):"缺少查询条件"));
        }
        return jsonObject.getString("data");
    }


    private static JSONArray getListData(String jsonString)throws Exception{
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        if(!"0000".equals(jsonObject.getString("code"))){
            throw new Exception("查询失败："+((jsonObject.has("msg"))?jsonObject.getString("msg"):"缺少查询条件"));
        }
        return jsonObject.getJSONArray("data");
    }


}
