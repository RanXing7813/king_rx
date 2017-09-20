package cn.com.taiji.tools.date;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 模块名：公用的时间类 版本号：1.0 创建时间：2010年4月13日 作 者：tj 详细功能说明：日期类型转换和日期之间计算的方法
 */
public class DateTimeUtil {
	
	Log log = LogFactory.getLog(DateTimeUtil.class); 

	public final int YYYY_MM_dd_hh_mm_ss = 1;
	public final int YYYY_MM_dd = 2;

	public final String YYYY = "yyyy";
	public final String YY = "yy";
	public final String MM = "MM";
	public final String DD = "dd";
	public final String hh = "HH";
	public final String mm = "mm";
	public final String ss = "ss";

	public String[] timeFormat = { YYYY, YY, MM, DD, hh, mm, ss };

	/**
	 * 获得标记时间的字符串
	 * 
	 * @param date
	 *            Date
	 * @param str
	 *            String
	 *            输入的字符串为带有标记的字符，YYYY或YY代表年，MM代表月，DD代表日，hh代表小时，mm代表分钟，ss代表秒
	 * @return String 返回空值则表示输入错误或者转化错误
	 */
	@SuppressWarnings("rawtypes")
	public String Date2String(Date date, String str) {
		StringBuffer tempBuffer = new StringBuffer();
		str = transString(str);
		if (str == null || str.equals("")) {
			return null;
		}
		String[] formatStr = getFormatString(str);
		if (formatStr != null && formatStr.length > 0) {
			for (int i = 0; i < formatStr.length; i++) {
				tempBuffer.append(formatStr[i] + "-");
			}
			String tempStr = tempBuffer.toString();
			tempStr = tempStr.substring(0, tempStr.length() - 1);
			SimpleDateFormat formater = new SimpleDateFormat(tempStr);
			String result = formater.format(date);
			if (result != null && result.length() > 0) {
				List divideList = divideString(str, formatStr);
				if (divideList != null && !divideList.isEmpty()) {
					String[] resultArray = result.split("-");
					boolean resultLength = resultArray.length == divideList
							.size() ? true : false;
					result = "";
					for (int i = 0; i < resultArray.length; i++) {
						result += (String) divideList.get(i) + resultArray[i];
					}
					if (!resultLength) {
						result += (String) divideList
								.get(divideList.size() - 1);
					}
					return result;
				} else {
					log.warn("分割字符出错！");
					return null;
				}
			} else {
				log.warn("转换时间出错！");
				return null;
			}
		} else {
			log.warn("时间格式不正确！");
			return null;
		}
	}

	/**
	 * 转化字符,基于时间的大小写转换
	 * 
	 * @param str
	 * @return String 返回空值表示错误
	 */
	private String transString(String str) {
		String[] trans1 = { "YY", "DD", "hh" };
		String[] trans2 = { "yy", "dd", "HH" };
		try {
			if (str != null && !str.equals("")) {
				for (int i = 0; i < trans1.length; i++) {
					str = str.replaceAll(trans1[i], trans2[i]);
				}
			} else {
				log.warn("字符为空，不能转化");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.warn("转化字符错误");
			return null;
		}
		return str;
	}

	/**
	 * 获取字符串中标示符的方法
	 * 
	 * @param format
	 *            String
	 * @return String[]
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String[] getFormatString(String format) {
		Map map = new HashMap();
		for (int i = 0; i < timeFormat.length; i++) {
			String tempString = format;
			int startStr = 0;
			while (i >= 0) {
				int pos = tempString.indexOf(timeFormat[i]);
				if (pos >= 0) {
					if (timeFormat[i] == "yy") { // 对yy特殊处理
						String t1 = (String) map
								.get(new Integer(pos + startStr));
						String t2 = (String) map.get(new Integer(pos + startStr
								- 2));
						if ((t1 != null && t1.equals("yyyy"))
								|| (t2 != null && t2.equals("yyyy"))) {
							int start = pos + timeFormat[i].length() > tempString
									.length() ? tempString.length()
									: (pos + timeFormat[i].length());
							tempString = tempString.substring(start, tempString
									.length());
							startStr += start;
							continue;
						}
					}
					map.put(new Integer(pos + startStr), timeFormat[i]);
					int start = pos + timeFormat[i].length() > tempString
							.length() ? tempString.length()
							: (pos + timeFormat[i].length());
					tempString = tempString.substring(start, tempString
							.length());
					startStr += start;
				} else {
					break;
				}
			}
		}
		Object[] tmp = map.keySet().toArray();
		Arrays.sort(tmp);
		String[] varNames = new String[tmp.length];
		for (int i = 0; i < varNames.length; i++) {
			varNames[i] = (String) map.get(tmp[i]);
		}
		return varNames;
	}

	/**
	 * 分割字符串
	 * 
	 * @param str
	 *            String
	 * @param divideStr
	 *            String[]
	 * @return List
	 */
	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	private List divideString(String str, String[] divideStr) {
		List returnList = new ArrayList();
		int start = 0;
		int end = 0;
		if (str != null && str.length() > 0 && divideStr != null
				&& divideStr.length > 0) {
			end = str.length();
			for (int i = 0; i < divideStr.length; i++) {
				int po = str.indexOf(divideStr[i]);
				if (po >= 0) {
					returnList.add(str.substring(0, po));
					start = po + divideStr[i].length();
					;
					str = str.substring(start, str.length());
				}
			}
			if (str != null && str.length() > 0) {
				returnList.add(str);
			}
		} else {
			return null;
		}
		return returnList;
	}

	/** */
	/**
	 * 把时间变量的值按顺序取出,以逗号隔开
	 * 
	 * @param val
	 * @return
	 */
	@SuppressWarnings("unused")
	private String[] getTimeInOriginOrder(String val) {
		String temp = "";
		String digital = "0123456789";
		for (int i = 0; i < val.length(); i++) {
			if (digital.indexOf(val.substring(i, i + 1)) > -1) {
				temp = temp + val.substring(i, i + 1);
			} else {
				if (temp.length() > 0 && temp.charAt(temp.length() - 1) != ',') {
					temp = temp + ",";
				}
			}
		}
		return temp.split(",");
	}

	// 获取全部数字
	public String getNumberFormString(String str) {
		StringBuffer strbuf = new StringBuffer();
		if (str != null) {
			for (int i = 0; i < str.length(); i++) {
				char c = str.charAt(i);
				if (c >= '0' && c <= '9') {
					strbuf.append(c);
				}
			}
		}
		return strbuf.toString();
	}

	/**
	 * @version 1.0
	 * @param szDate
	 *            字符串
	 * @see 字符串转换成日期类型 格式为yyyy-MM-dd
	 */
	public Date String2Date(String szDate) {
		return String2Date(szDate, 2);
	}

	/**
	 * @version 1.0
	 * @param szDate
	 *            字符串
	 * @param option
	 *            数值转换的格式 1->yyyy-MM-dd HH:mm:ss 2->yyyy-MM-dd
	 * @see 字符串转换成日期类型
	 */
	public Date String2Date(String szDate, int option) {
		try {
			String pattern = "yyyy-MM-dd HH:mm:ss";
			switch (option) {
			case 1:
				break;
			case 2: {
				pattern = "yyyy-MM-dd";
				break;
			}
			}
			SimpleDateFormat formater = new SimpleDateFormat(pattern);
			Date d = formater.parse(szDate);
			return d;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 转换时间戳类型 format:yyyy-mm-dd hh:mm:ss.fffffffff
	 * 
	 * @param sDt
	 *            String
	 * @return Timestamp
	 */
	public java.sql.Timestamp String2TimeStamp(String sDt) {
		if (sDt == null || "".equals(sDt.trim())) {
			return null;
		}
		try {
			// sDt format:yyyy-mm-dd hh:mm:ss.fffffffff
			return java.sql.Timestamp.valueOf(sDt);
		} catch (IllegalArgumentException iae) {
			sDt = sDt + " 00:00:00";
			return java.sql.Timestamp.valueOf(sDt);
		}
	}

	/**
	 * @version 1.0
	 * @param date
	 *            要转换的日期
	 * @see 日期类型转换成字符串 格式为yyyy-MM-dd
	 */
	public String Date2String(Date date) {
		return Date2String(date, 2);
	}

	/**
	 * @version 1.0
	 * @param date
	 *            要转换的日期
	 * @param option
	 *            数值转换的格式 1->yyyy-MM-dd HH:mm:ss 2->yyyy-MM-dd
	 * @see 日期类型转换成字符串
	 */
	public String Date2String(Date date, int option) {
		try {
			String pattern = "yyyy-MM-dd HH:mm:ss";
			switch (option) {
			case 1:
				break;
			case 2: {
				pattern = "yyyy-MM-dd";
				break;
			}
			}
			SimpleDateFormat formater = new SimpleDateFormat(pattern);
			String result = formater.format(date);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// public int[] Str2IntArray(String szIds,String patten){
	// StringTokenizer token = new StringTokenizer(szIds,patten);
	// int length = token.countTokens();
	// int[] result = new int[length];
	// int i=0;
	// while(token.hasMoreTokens()){
	// String str = token.nextToken();
	// result[i] = Integer.parseInt(str);
	// i++;
	// }
	// return result;
	// }

	// public String[] Str2StrArray(String str,String patten){
	// StringTokenizer token = new StringTokenizer(str,",");
	// int length = token.countTokens();
	// String[] result = new String[length];
	// int i=0;
	// while(token.hasMoreTokens()){
	// result[i]=token.nextToken();
	// i++;
	// }
	// return result;
	// }

	// public String substring(String str,int sublength){
	// if(str==null) return "";
	// String result="";
	// if(str.length()<sublength){
	// result = str;
	// }else{
	// result = str.substring(0,sublength);
	// result += ".....";
	// }
	// return result;
	// }

	/**
	 * @author tj
	 * @version 1.0
	 * @see 得到2日期间的相隔天数
	 * @param beginDay
	 *            字符型日期
	 * @param endDay
	 *            字符型日期
	 * @exception Exception
	 */
	public long getDaysBetween(String beginDay, String endDay) {
		if (beginDay == null || beginDay.equals(""))
			return 0;
		if (endDay == null || endDay.equals(""))
			return 0;
		long day = 0;
		try {
			java.util.Date date = String2Date(beginDay, 2);
			java.util.Date mydate = String2Date(endDay, 2);

			day = (date.getTime() - mydate.getTime()) / (60 * 60 * 1000);
		} catch (Exception e) {
			return 0;
		}
		return day;
	}

	/**
	 * @author tj
	 * @version 1.0
	 * @see 得到2日期间的相隔天数
	 * @param beginDay
	 *            日期
	 * @param endDay
	 *            日期
	 * @exception Exception
	 */
	public long getDaysBetween(Date beginDay, Date endDay) {
		long day = (beginDay.getTime() - endDay.getTime())
				/ (24 * 60 * 60 * 1000);
		return day;
	}

	public long getHoursBetween(String beginDay, String endDay) {
		if (beginDay == null || beginDay.equals(""))
			return 0;
		if (endDay == null || endDay.equals(""))
			return 0;
		long day = 0;
		try {
			java.util.Date date = String2Date(beginDay, 2);
			java.util.Date mydate = String2Date(endDay, 2);

			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return 0;
		}
		return day;
	}

	/**
	 * @author tj
	 * @version 1.0
	 * @see 获取当前时间
	 * @param option
	 *            日期格式 ：这里日期为标准的JAVA日期格式yyyy->年.MM->月dd->日 HH->时mm->分:ss->秒
	 *            1代表yyyy-MM-dd HH:mm:ss 2代表yyyy-MM-dd 3代表yyyyMMdd
	 *            4代表yyyyMMddHHmmss 5代表yyyy-MM-dd HH:mm:ss:ffffff
	 */
	public String getNowTime(int option) {

		String pattern = "yyyy-MM-dd HH:mm:ss";
		switch (option) {
		case 1:
			break;
		case 2: {
			pattern = "yyyy-MM-dd";
			break;
		}
		case 3: {
			pattern = "yyyyMMdd";
			break;
		}
		case 4: {
			pattern = "yyyyMMddHHmmss";
			break;
		}
		case 5: {
			pattern = "yyyy/MM/dd";
			break;
		}
		case 6: {
			pattern = "yyyy";
			break;
		}
		}
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);// 可以方便地修改日期格式
		String nowTime = dateFormat.format(now);
		return nowTime;
	}

	/**
	 * @author tj
	 * @version 1.0
	 * @see 获取当前时间
	 */
	public Date getNowTime() {
		Date now = new Date();
		return now;
	}

	/**
	 * @author tj
	 * @version 1.0
	 * @see 获得几天前的日期：给定日期的前几天的日期
	 * @param date
	 *            日期
	 * @param day
	 *            数值类型
	 */
	public Date getDateBefore(Date date, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}
	
	public String getDateBefore(Date date, int day, int option) {
		
		String pattern = "yyyy-MM-dd HH:mm:ss";
		switch (option) {
		case 1:
			break;
		case 2: {
			pattern = "yyyy-MM-dd";
			break;
		}
		case 3: {
			pattern = "yyyyMMdd";
			break;
		}
		case 4: {
			pattern = "yyyyMMddHHmmss";
			break;
		}
		case 5: {
			pattern = "yyyy/MM/dd";
			break;
		}
		case 6: {
			pattern = "yyyy";
			break;
		}
		case 7: {
			pattern = "yyyyMM";
			break;
		}
		}
		
		
		
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);// 可以方便地修改日期格式
		String nowTime = dateFormat.format(now.getTime());
		return nowTime;
	}

	/**
	 * @author tj
	 * @version 1.0
	 * @see 获得几天后的时间:给定日期的后几天的日期
	 * @param date
	 *            日期
	 * @param day
	 *            数值类型
	 */
	public Date getDateAfter(Date date, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

	/**
	 * 
	 * @名称：判断时间是上午还是下午
	 * @作者：钟代冬
	 * @创建时间： Aug 19, 2014 2:18:52 PM
	 * @说明：//结果为“0”是上午 结果为“1”是下午
	 */
	public int getDateAm_Pm(Date date) {
		GregorianCalendar ca = new GregorianCalendar();
		ca.setTime(date);
		return ca.get(GregorianCalendar.AM_PM);
	}

	public static int compare_date(String paramString1, String paramString2) {
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd");
		try {
			Object localObject = null;
			localObject = localSimpleDateFormat.parse(paramString1);
			Date localDate = localSimpleDateFormat.parse(paramString2);
			if (((Date) localObject).getTime() > localDate.getTime())
				return 1;
			if (((Date) localObject).getTime() < localDate.getTime())
				return -1;
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
