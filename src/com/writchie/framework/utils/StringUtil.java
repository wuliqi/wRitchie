package com.writchie.framework.utils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * @author 吴理理
 *
 */
public class StringUtil {
	
	
	/**
	 * 将null值转""
	 * @param srcStr
	 * @return
	 */
	public static String trimNull(String srcStr)
	{
		String result="";
		if(!(null==srcStr||srcStr.equalsIgnoreCase("null")))
		{
			result=srcStr;
		}
		return result;
	}
	
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return true:表示空，false：表示不空
	 */
	public static boolean isEmpty(String str)
	{
		boolean flag=false;
		if("null".equals(str)||null==str||"".equals(str)||str.trim().length()<1)
			flag=true;
		return flag;
	}
	
	/**
	 * 根据传入的长度，自动截取并加省略...
	 * @param src
	 * @param len
	 * @return
	 */
	public static String stringEllipsis(String src,int len){
		String tmpStr="";
		if(src.length()>len){
			tmpStr=src.substring(0, len)+"...";
		}else{
			tmpStr=src;
		}
		return tmpStr;
	}

	/**
	 * 判断手机号码是否正确
	 * @param mobile
	 * @return true:表示正确   false：表示错误 
	 */
	public static boolean isMobileNumber(String mobile) {
		boolean flag=false;
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobile);
        flag=m.matches();
        return flag;
    }
	/**
	 * 判断对象是否为空
	 * @param str
	 * @return
	 */
	public static boolean isNotNull(Object str) {
		return str != null;
	}

}
