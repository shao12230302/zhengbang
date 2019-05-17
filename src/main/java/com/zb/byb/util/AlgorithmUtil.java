package com.zb.byb.util;

import com.zb.byb.common.C;

import java.util.ArrayList;
import java.util.List;

/**
 * 对比算法
 * 
 * @author xieli
 *
 */
public class AlgorithmUtil {
	
//	public static void main(String[] args) {
//		System.out.println(doAssert("{1,8，2.0}", 2));
//	}

	/**
	 * 
	 * @param region 区间 支持下列三种描述方式 开区间 () ，如 (1,10) 1<a<10 时满足 闭区间[] ,如 [20,30)
	 *               20=<a<30 时满足 枚举：{} 如：{1,2,3,4} a 是1,2,3,4中的一种则满足
	 * @param a
	 * @return
	 */
	public static boolean doAssert(String region, double a) {
		if (C.checkNullOrEmpty(region))
			return false;
		
		// 1.进行转化，将中文标识和空格清掉
		region = region.replace(" ", "").replace("（", "(").replace("）", ")").replace("｛", "{").replace("｝", "}").replace("【", "]").replace("】", "]").replace("，", ",");
		
		// 2.进行数据检验
		if (!validateReginon(region))
			return false;
		
//		System.out.println(region);
		String startStr = C.parseStr(region.charAt(0));
		String endStr = C.parseStr(region.charAt(region.length() - 1));
		// 3.1 算法比较，{1,2,3,4} a 是1,2,3,4中的一种则满足
		if (startStr.equals("{") && endStr.equals("}"))
		{
			return containsData(region, a);
		}	
		
		// 3.2 开区间 () ，如 (1,10) 1<a<10 时满足 闭区间[] ,如 [20,30) 20=<a<30 时满足
		return isExistData(region, a);
	}
	
	/**
	 * 开区间 () ，如 (1,10) 1<a<10 时满足 闭区间[] ,如 [20,30) 20=<a<30 时满足
	 * @param region
	 * @param a
	 * @return
	 */
	private static boolean isExistData(String region, double a)
	{
		if (C.checkNullOrEmpty(region))
			return false;
		
		String startStr = C.parseStr(region.charAt(0));
		String endStr = C.parseStr(region.charAt(region.length() - 1));
		List<String> list = getDoubleValueList(region);
		if (list.size() != 2 || C.checkNullOrEmpty(startStr) || C.checkNullOrEmpty(endStr))
			return false;
		
		double startValue = C.parseDbl(list.get(0));
		double endValue = C.parseDbl(list.get(1));
		
		// 左边开区间
		if ("(".equals(startStr) && a > startValue)
		{
			return rightValueCompare(endStr, endValue, a);
			
		}	
		else if ("[".equals(startStr) && a >= startValue)
		{
			// 左边闭区间
			return rightValueCompare(endStr, endValue, a);
		}	
		
		return false;
	}
	
	/**
	 *  进行数据对比， 
	 * @param endStr
	 * @param endValue
	 * @param a
	 * @return
	 */
	private static boolean rightValueCompare(String endStr, double endValue, double a)
	{
		if (C.checkNullOrEmpty(endStr))
			return false;
		
		// 右边开区间
		if (")".equals(endStr) && a < endValue)
		{
			return true;
		}	
		else if ("]".equals(endStr) && a <= endValue)
		{
			// 右边闭区间
			return true;
		}	
		
		return false;
	}
	
	
	/**
	 * "{}"大括号进行数据匹配数据
	 * @param region
	 * @param a
	 * @return
	 */
	private static boolean containsData(String region, double a) 
	{
		if (C.checkNullOrEmpty(region))
			return false;
		
		List<String> list = getDoubleValueList(region);
		if (list == null || list.size() <= 0)
			return false;
			
		// 进行遍历数据，每一个数据进行匹配
		for (String value : list) {
			if (a == C.parseDbl(value))
				return true;
		}
		
		return false;
	}
	
	/**
	 * 进行数据截取
	 * @param region
	 * @return
	 */
	private static List<String> getDoubleValueList(String region)
	{
		if (C.checkNullOrEmpty(region))
			return new ArrayList<>();
		
		String regionStr = region.substring(1, region.length() - 1);
		return  C.arrayToList(regionStr.split(","));
		
	}
	
	
	/**
	 * 校验表达式
	 * @param region
	 * @return
	 */
	private static boolean validateReginon(String region)
	{
		if (C.checkNullOrEmpty(region))
			return false;
		
		List<String> signList = new ArrayList<String>();
		signList.add("(");
		signList.add(")");
		signList.add("{");
		signList.add("}");
		signList.add("[");
		signList.add("]");
		
		String startStr = C.parseStr(region.charAt(0));
		String endStr = C.parseStr(region.charAt(region.length() - 1));
		// 开始字段和结束字段，都满足，则返回true,表示验证通过
		if (signList.contains(startStr) && signList.contains(endStr))
			return true;
		
		return false;
	}
	
}
