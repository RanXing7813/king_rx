package cn.com.taiji.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * 关于获得日期以及相应转换方法的公用类
 * 最全版本
 * @author 迟雪
 *
 */
public class DateUtil {
	//用来全局控制 上一周，本周，下一周的周数变化
	public static  int weeks = 0;
	/**
	 * 一月最大天数
	 */
	public static int MaxDate;//一月最大天数
	/**
	 * 一年最大天数
	 */
	public static int MaxYear;//一年最大天数
	
	public final static String YYYY = "yyyy";
	public final static String YY = "yy";
	public final static String MM = "MM";
	public final static String DD = "dd";
	public final static String hh = "HH";
	public final static String mm = "mm";
	public final static String ss = "ss";

	public static String[] timeFormat = { YYYY, YY, MM, DD, hh, mm, ss };
	private static HashMap map;


	static {
		map = new HashMap();
		map.put(new Character('0'), "零");
		map.put(new Character('1'), "一");
		map.put(new Character('2'), "二");
		map.put(new Character('3'), "三");
		map.put(new Character('4'), "四");
		map.put(new Character('5'), "五");
		map.put(new Character('6'), "六");
		map.put(new Character('7'), "日");
	}
	
	
	
//	public static void main(String a[]) {
		/*
		Date d = getNowTime();
		System.out.println("-------------------------------------日期转换-----------------------------------------------------------------");
		Date d = getNowTime();
		System.out.println("当前日期转换成字符串类型（yyyy-MM-dd HH:mm:ss）："+getNowTime(1));
		System.out.println("当前日期转换成字符串类型（yyyy-MM-dd）："+getNowTime(2));
		System.out.println("当前日期转换成字符串类型（yyyyMMdd）："+getNowTime(3));
		System.out.println("当前日期转换成字符串类型（yyyyMMddHHmmss）："+getNowTime(4));
		System.out.println("当前日期根据格式转换："+getNowTime("yyyy-MM-dd"));
		System.out.println("日期转换成字符串形式（格式任意）："+Date2String(d,"[YY年MM月dd日]"));
		System.out.println("日期转换成字符串形式（格式任意）："+Date2String(d,1));
		System.out.println("日期转换成字符串形式（格式任意）："+Date2String(d,2));
		System.out.println("根据数字获得对应的字符用于日期："+getNumberString("2012年12月2日"));
		System.out.println("字符串转换成日期类型（YYYY-MM-dd）："+String2Date("2012-07-02"));
		System.out.println("字符串转换成日期类型(yyyy-MM-dd HH:mm:ss)："+String2Date("2012-07-02 14:00:00",1));
		System.out.println("字符串转换成日期类型（YYYY-MM-dd）："+String2Date("2012-07-02",2));
		System.out.println("字符串转换成日期类型Timestamp（yyyy-mm-dd hh:mm:ss.fffffffff）："+String2TimeStamp("2012-07-02"));
		
		System.out.println("--------------------------------------日期计算----------------------------------------------------------------");
		System.out.println("二个字符串日期相差天数计算"+getDaysBetween("2009-04-05","2012-07-02"));
		System.out.println("二个日期相差天数计算"+getDaysBetween(getNowTime(),String2Date("2009-04-05")));
		System.out.println("获得几天前的日期：给定日期的前几天的日期"+getDateBefore(getNowTime(),240));
		System.out.println("获得几天前的日期：给定日期的前几天的日期"+getDateAfter(getNowTime(),240));
		String season[]=getThisSeason(11);
		System.out.println("根据月份获得季度"+season[0]+"~~"+season[1]);
		System.out.println("获得某年某月的最后一天"+getLastDay(2012,5));
		String date[]=new String[2];
		date[0]="2009-12-01";
		date[1]="2009-10-01";
		String day[]=groupDate(date);
		System.out.println("根据字符串日期数组有小到大排序"+day[0]+"<-"+day[1]);
		
		System.out.println("--------------------------------------获得日期----------------------------------------------------------------");
		System.out.println("获得本周星期一的日期"+Date2String(getMonday(),"yyyy-MM-dd"));
		System.out.println("获得本周星期二的日期"+Date2String(getTuesday(),"yyyy-MM-dd"));
		System.out.println("获得本周星期三的日期"+Date2String(getWednesday(),"yyyy-MM-dd"));
		System.out.println("获得本周星期四的日期"+Date2String(getThursday(),"yyyy-MM-dd"));
		System.out.println("获得本周星期五的日期"+Date2String(getFriday(),"yyyy-MM-dd"));
		System.out.println("获得本周星期六的日期"+Date2String(getSaturday(),"yyyy-MM-dd"));
		System.out.println("获得本周星期日的日期"+Date2String(getSunday(),"yyyy-MM-dd")+"====================================================遗留问题");
		System.out.println("今天是"+getCurrentWeek());
		System.out.println("获得上周日"+Date2String(getPreviousWeekSunday(),2));
		System.out.println("获得上周一"+Date2String(getPreviousWeekMonday(),2));
		System.out.println("获得下周一"+Date2String(getNextWeekMonday(),2));
		System.out.println("获得下周日"+Date2String(getNextWeekSunday(),2));
		System.out.println("获得日期获得当前周全年的第几周："+getWeekOfYear(d));
		System.out.println("获得日期获得当前年有多少周："+getMaxWeekNumOfYear(2012));
		System.out.println("获得到某年某周的第一天 ："+getFirstDayOfWeek(2012,4));
		System.out.println("获得到某年某周的最后一天 ："+getLastDayOfWeek(2012,4));
		System.out.println("获得指定日期所在周的第一天："+getFirstDayOfWeek(d));
		System.out.println("获得指定日期所在周的最后一天"+getLastDayOfWeek(d));
		System.out.println("根据参数获得当前日期前几周的星期一的日期"+getPreviousFewWeekday(5));
		System.out.println("根据参数获得当前日期前几周的星期日的日期"+getPreviousFewWeekSunday(5));
		
		System.out.println("获得本月第一天"+DateToDate(getCurrentMonthFirst(),"yyyy-mm-dd"));
		System.out.println("获得本月最后一天"+DateToDate(getCurrentMonthLast(),"yyyy-MM-dd"));
		*/
		//System.out.println(Date2String(String2Date("2016-09-30 19:40:39.0",1),1));
		
	//	System.out.println(getNowTime(1));
		/*Date date=String2Date("2012-10-01");
		System.out.println("计算当前日期，前几个月最后一天,返回字符串"+getDefaultDay(date,2));
		System.out.println("计算当前日期，前几个月第一天,返回字符串"+getFirstDay(date,2));
		
		
		
		
         GregorianCalendar gc = new GregorianCalendar();
         gc.add(GregorianCalendar.MONTH,1);
         gc.add(GregorianCalendar.DATE,-date.getDate());
         DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
         Date dateTemp = gc.getTime();
         String s = df.format(dateTemp);
         System.out.println(s);*/

		
		
		
		
		
		
	/*	System.out.println("获得上月第一天"+Date2String(getPreviousMonthFirst(),2));
		System.out.println("获得上月最后一天"+Date2String(getPreviousMonthLast(),2));
		System.out.println("获得本月第一天"+Date2String(getCurrentMonthFirst(),2));
		System.out.println("获得本月最后一天"+Date2String(getCurrentMonthLast(),2));
		System.out.println("获得下月第一天"+Date2String(getNextMonthFirst(),2));
		System.out.println("获得下月最后一天"+Date2String(getNextMonthLast(),2));
		
		
		
		
		System.out.println("本年有多少天"+getCurrentYearNumber());
		System.out.println("是否是闰年"+isLeapYear(2009));
		System.out.println("获得上年第一天"+getPreviousYearFirst());
		System.out.println("获得上年最后一天"+Date2String(getPreviousYearLast(),2));
		System.out.println("获得本年第一天"+Date2String(getCurrentYearFirst(),2));
		System.out.println("获得本年最后一天"+Date2String(getCurrentYearLast(),2));
		System.out.println("获得明年最后一天"+Date2String(getNextYearFirst(),2));
		System.out.println("获得明年最后一天"+Date2String(getNextYearLast(),2));
		
		System.out.println("获得某年某月某周星期几的日期 "+weekdatetodata(2009, 2, 2, 3)); 
		System.out.println("根据获取某年某月的最后一天:"+getLastDayOfMonth(2009,6));*/
		
//	}
/**************************************日期转换方法**************************************/
	/**
	 * 指定日期格式获取当前时间
	 * @author 迟雪
	 * @param pattern
	 * @return
	 */
	public static String getNowTime(String pattern) {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);// 可以方便地修改日期格式
		String nowTime = dateFormat.format(now);
		return nowTime;
	}
	
	public static List getListTowDaysDates(String sDate,String eDate) throws ParseException {
		List dateList = new ArrayList();
		dateList.add(sDate);
		Calendar startCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(sDate);
		startCalendar.setTime(startDate);
		Date endDate = df.parse(eDate);
		endCalendar.setTime(endDate);
		while (true) {
			startCalendar.add(Calendar.DAY_OF_MONTH, 1);
			if (startCalendar.getTimeInMillis() < endCalendar.getTimeInMillis()) {// TODO
				dateList.add(df.format(startCalendar.getTime()));
			} else {
				break;
			}
		}
		dateList.add(eDate);
		return dateList;
	}

	
	/**
	 * 指定日期格式获取当前时间(日期转日期格式)
	 * @author 迟雪
	 * @param pattern
	 * @return
	 */
	public static Date DateToDate(Date time,String pattern) {
		SimpleDateFormat dateFor = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
		String datetime=dateFor.format(time);
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);// 可以方便地修改日期格式
		try {
			time = dateFormat.parse(datetime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}


	/**
	 *  获取当前时间
	 * @author 迟雪
	 * @version 1.0
	 * 
	 * @param option
	 *            日期格式 ：这里日期为标准的JAVA日期格式yyyy->年.MM->月dd->日 HH->时mm->分:ss->秒
	 *            1代表yyyy-MM-dd HH:mm:ss 2代表yyyy-MM-dd 3代表yyyyMMdd
	 *            4代表yyyyMMddHHmmss 5代表yyyy-MM-dd HH:mm:ss:ffffff
	 */
	public static String getNowTime(int option) {

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
		}
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);// 可以方便地修改日期格式
		String nowTime = dateFormat.format(now);
		return nowTime;
	}
	
	/**
	 *@author 迟雪
	 *@version 1.0 
	 *@param str String
	 * 字符转换时间戳类型 format:yyyy-mm-dd hh:mm:ss.fffffffff
	 */
	static public java.sql.Timestamp String2TimeStamp(String str) {
		if (str == null || "".equals(str.trim())) {
			return null;
		}
		try {
			return java.sql.Timestamp.valueOf(str);
		} catch (IllegalArgumentException iae) {
			str = str + " 00:00:00";
			return java.sql.Timestamp.valueOf(str);
		}
	}
	/**
	 *   字符串转换成日期类型 格式为yyyy-MM-dd
	 * @version 1.0
	 * @param szDate
	 *            字符串
	 *
	 */
	public static Date String2Date(String szDate) {
		return String2Date(szDate, 2);
	}
/**
 *  字符串转换成日期类型
 * @version 1.0
 * @param szDate
 *            字符串
 * @param option
 *            数值转换的格式 1->yyyy-MM-dd HH:mm:ss 2->yyyy-MM-dd
 * 
 */
public static Date String2Date(String szDate, int option) {
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
	 * 根据数字获得对应的字符用于日期
	 * @param str
	 * @return
	 */
	public static String getNumberString(String str) {
		char ch[] = str.toCharArray();
		StringBuffer buffer = new StringBuffer();
		Character character;
		for (int i = 0; i < ch.length; i++) {
			character = new Character(ch[i]);
			buffer.append(map.get(character)==null?ch[i]:map.get(character));
		}
		return buffer.toString();
	}
	/**
	 *  获取当前时间
	 * @author 迟雪
	 * @version 1.0
	 * 
	 */
	public static Date getNowTime() {
		Date now = new Date();
		return now;
	}
	/**
	 * 根据格式将字符串转化成日期格式
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date String2Date(String date, String format) {
		try {
			SimpleDateFormat formater = new SimpleDateFormat(format);
			Date d = formater.parse(date);
			return d;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 日期类型转换成字符串
	 * @version 1.0
	 * @param date
	 *            要转换的日期
	 *  日期类型转换成字符串 格式为yyyy-MM-dd
	 */
	public static String Date2String(Date date) {
		return Date2String(date, 2);
	}

	/**
	 *  指定格式日期类型转换成字符串
	 * @version 1.0
	 * @param date
	 *            要转换的日期
	 * @param option
	 *            数值转换的格式 1->yyyy-MM-dd HH:mm:ss 2->yyyy-MM-dd
	 *  日期类型转换成字符串
	 */
	public static String Date2String(Date date, int option) {
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
	@SuppressWarnings("unchecked")
	public static String Date2String(Date date, String str) {
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
					System.out.println("分割字符出错！");
					return null;
				}
			} else {
				System.out.println("转换时间出错！");
				return null;
			}
		} else {
			System.out.println("时间格式不正确！");
			return null;
		}
	}
	/**
	 * 转化字符,基于时间的大小写转换
	 * @param str
	 * @return String 返回空值表示错误
	 */
	private static String transString(String str) {
		String[] trans1 = { "YY", "DD", "hh" };
		String[] trans2 = { "yy", "dd", "HH" };
		try {
			if (str != null && !str.equals("")) {
				for (int i = 0; i < trans1.length; i++) {
					str = str.replaceAll(trans1[i], trans2[i]);
				}
			} else {
				System.out.println("字符为空，不能转化");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("转化字符错误");
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
	@SuppressWarnings("unchecked")
	private static String[] getFormatString(String format) {
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
	private static List divideString(String str, String[] divideStr) {
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
	/**************************************日期转换方法**************************************/
	/**********************************************日期计算*********************************/
	/**
	 *  得到2日期间的相隔天数
	 * @param beginDay
	 *            字符型日期
	 * @param endDay
	 *            字符型日期
	 * @exception Exception
	 */
	public static long getDaysBetween(String beginDay, String endDay) {
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
	 *  得到2日期间的相隔天数
	 * @param beginDay
	 *            日期
	 * @param endDay
	 *            日期
	 * @exception Exception
	 */
	public static long getDaysBetween(Date beginDay, Date endDay) {
		long day = (beginDay.getTime() - endDay.getTime())
		/ (24 * 60 * 60 * 1000);
		return day;
	}



	/**
	 *  获得几天前的日期：给定日期的前几分钟的日期
	 * @param date
	 *            日期
	 * @param day
	 *            数值类型
	 */
	public static Date getDateBeforeMM(Date date, int mm) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) + mm);
		return now.getTime();
	}



	/**
	 *  获得几天前的日期：给定日期的前几天的日期
	 * @param date
	 *            日期
	 * @param day
	 *            数值类型
	 */
	public static Date getDateBefore(Date date, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	/**
	 *  获得几天后的时间:给定日期的后几天的日期
	 * @param date
	 *            日期
	 * @param day
	 *            数值类型
	 */
	public static Date getDateAfter(Date date, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

	/**
	 * 根据月份获得月份所在季度是从哪天到哪天 eg:getThisSeason(4)->字符串数组seasonDate=[2010-4-1][2010-6-30]
	 *@author 迟雪
	 *@version 1.0 
	 *@param month 月份
	 *  
	 */
	public static String[] getThisSeason(int month){
		int array[][] = {{1,2,3},{4,5,6},{7,8,9},{10,11,12}};
		int season = 1;
		if(month>=1&&month<=3){
			season = 1;
		}
		if(month>=4&&month<=6){
			season = 2;
		}
		if(month>=7&&month<=9){
			season = 3;
		}
		if(month>=10&&month<=12){
			season = 4;
		}
		int start_month = array[season-1][0];
		int end_month = array[season-1][2];

		Date date = new Date();
		SimpleDateFormat   dateFormat   =   new   SimpleDateFormat("yyyy");//可以方便地修改日期格式   
		String  years  = dateFormat.format(date);   
		int years_value = Integer.parseInt(years);

		int start_days =1;//years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
		int end_days = getLastDay(years_value,end_month);
		String seasonDate[]=new String[2];
		seasonDate[0]=years_value+"-"+start_month+"-"+start_days;
		seasonDate[1]=years_value+"-"+end_month+"-"+end_days;
		return seasonDate;

	}
	/**
	 * 获取某年某月的最后一天
	 *@author 迟雪
	 *@version 1.0 
	 *@param year 年
	 *@param month 月
	 *  
	 */
	public static int getLastDay(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
				|| month == 10 || month == 12) {
			return 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
		return 0;
	}
	/**
	 *  是否闰年
	 *@author 迟雪
	 *@version 1.0 
	 *@param year 年
	 * 
	 *@return false代表不是 true 代表是
	 */
	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}


/********************************获得日期*****************************************************/
	/**
	 * 获得本周日
	 *@author 迟雪
	 *@version 1.0 
	 * 
	 */ 
	public   static   Date   getSunday(){       
		Calendar   c   =   Calendar.getInstance();   
		Date date=new Date();
		c.setTime(date);       
		c.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);       
		return c.getTime();   
	}        
	/**
	 * 获得本周一
	 *@author 迟雪
	 *@version 1.0 
	 * 
	 */ 
	public   static   Date   getMonday(){    
		Date date=new Date();
		Calendar   c   =   Calendar.getInstance();       
		c.setTime(date);       
		c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);       
		return c.getTime(); 
	}       
	/**
	 *  获得本周二
	 *@author 迟雪
	 *@version 1.0 
	 *
	 */ 
	public   static   Date   getTuesday(){ 
		Date date=new Date();
		Calendar   c   =   Calendar.getInstance();       
		c.setTime(date);       
		c.set(Calendar.DAY_OF_WEEK,Calendar.TUESDAY);       
		return c.getTime();     
	}     
	/**
	 * 获得本周三
	 *@author 迟雪
	 *@version 1.0 
	 * 
	 */  
	public   static   Date   getWednesday(){    
		Date date=new Date();
		Calendar   c   =   Calendar.getInstance();       
		c.setTime(date);       
		c.set(Calendar.DAY_OF_WEEK,Calendar.WEDNESDAY);       
		return c.getTime(); 
	}     
	/**
	 * 获得本周四
	 *@author 迟雪
	 *@version 1.0 
	 * 
	 */ 
	public   static   Date   getThursday(){      
		Date date=new Date();
		Calendar   c   =   Calendar.getInstance();       
		c.setTime(date);       
		c.set(Calendar.DAY_OF_WEEK,Calendar.THURSDAY);       
		return c.getTime();
	}     
	/**
	 * 获得本周五
	 *@author 迟雪
	 *@version 1.0 
	 * 
	 */ 
	public   static   Date   getFriday(){ 
		Date date=new Date();
		Calendar   c   =   Calendar.getInstance();       
		c.setTime(date);       
		c.set(Calendar.DAY_OF_WEEK,Calendar.FRIDAY);             
		return c.getTime();    
	}       
	/**
	 * 获得本周六
	 *@author 迟雪
	 *@version 1.0 
	 * 
	 */ 
	public   static   Date   getSaturday(){   
		Date date=new Date();
		Calendar   c   =   Calendar.getInstance();       
		c.setTime(date);       
		c.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);       
		return c.getTime();    
	}  

	/**
	 * 获取当前时间是星期几 
	 *@author 迟雪
	 *@version 1.0 
	 *@return String 星期几->汉字   
	 */   
	public static String getCurrentWeek() {    
		Date today = new Date(System.currentTimeMillis());    
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E");    
		return simpleDateFormat.format(today);    
	}  

	/**
	 * 获得上周星期日的日期
	 *@author 迟雪
	 *@version 1.0 
	 * 
	 */
	public static Date getPreviousWeekSunday() {
		weeks=0;
		weeks--;
		int mondayPlus = getMondayPlus();
		GregorianCalendar preSundayDate = new GregorianCalendar();
		preSundayDate.add(GregorianCalendar.DATE, mondayPlus+weeks);
		Date date = preSundayDate.getTime();
		//String preSunday = DateTimeUtil.Date2String(date,2);
		return date;
	}

	/**
	 *  获得上周星期一的日期
	 *@author 迟雪
	 *@version 1.0 
	 *
	 */
	public static Date getPreviousWeekMonday() {
		weeks--;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
		Date date = currentDate.getTime();
		//String preMonday = DateTimeUtil.Date2String(date,2);
		return date;
	}
	/**
	 * 获得下周星期一的日期
	 *@author 迟雪
	 *@version 1.0 
	 *  
	 */
	public static Date getNextWeekMonday() {
		weeks++;
		int mondayPlus = getMondayPlus();
		GregorianCalendar mondayDate = new GregorianCalendar();
		mondayDate.add(GregorianCalendar.DATE, mondayPlus + 7);
		Date date = mondayDate.getTime();
		//String nextMonday = DateTimeUtil.Date2String(date,2);
		return date;
	}

	/**
	 *  获得下周星期日的日期
	 *@author 迟雪
	 *@version 1.0 
	 * 
	 */
	public static Date getNextWeekSunday() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar sundayDate = new GregorianCalendar();
		sundayDate.add(GregorianCalendar.DATE, mondayPlus + 7+6);
		Date date = sundayDate.getTime();
		//String nextSunday = DateTimeUtil.Date2String(date,2);
		return date;
	}
	
	/** 
	 * 取得当前日期是多少周 
	 * 
	 * @param date 
	 * @return 
	 */  
	 public static int getWeekOfYear(Date date) {  
	 Calendar c = new GregorianCalendar();  
	 c.setFirstDayOfWeek(Calendar.MONDAY);  
	 c.setMinimalDaysInFirstWeek(7);  
	 c.setTime (date);  
	  
	 return c.get(Calendar.WEEK_OF_YEAR);  
	 }  
	  
	 /** 
	 * 得到某一年周的总数 
	 * 
	 * @param year 
	 * @return 
	 */  
	 public static int getMaxWeekNumOfYear(int year) {  
	 Calendar c = new GregorianCalendar();  
	 c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);  
	  
	 return getWeekOfYear(c.getTime());  
	 }  
	 
	  
	 /** 
	 * 得到某年某周的第一天 
	 * 
	 * @param year 
	 * @param week 
	 * @return 
	 */  
	 public static Date getFirstDayOfWeek(int year, int week) {  
	 Calendar c = new GregorianCalendar();  
	 c.set(Calendar.YEAR, year);  
	 c.set (Calendar.MONTH, Calendar.JANUARY);  
	 c.set(Calendar.DATE, 1);  
	  
	 Calendar cal = (GregorianCalendar) c.clone();  
	 cal.add(Calendar.DATE, week * 7);  
	  
	 return getFirstDayOfWeek(cal.getTime ());  
	 }  
	  
	 /** 
	 * 得到某年某周的最后一天 
	 * 
	 * @param year 
	 * @param week 
	 * @return 
	 */  
	 public static Date getLastDayOfWeek(int year, int week) {  
	 Calendar c = new GregorianCalendar();  
	 c.set(Calendar.YEAR, year);  
	 c.set(Calendar.MONTH, Calendar.JANUARY);  
	 c.set(Calendar.DATE, 1);  
	  
	 Calendar cal = (GregorianCalendar) c.clone();  
	 cal.add(Calendar.DATE , week * 7);  
	  
	 return getLastDayOfWeek(cal.getTime());  
	 }  
	  
	 /** 
	 * 取得指定日期所在周的第一天 
	 * 
	 * @param date 
	 * @return 
	 */  
	 public static Date getFirstDayOfWeek(Date date) {  
	 Calendar c = new GregorianCalendar();  
	 c.setFirstDayOfWeek(Calendar.MONDAY);  
	 c.setTime(date);  
	 c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday   
	 return c.getTime ();  
	 }  
	  
	 /** 
	 * 取得指定日期所在周的最后一天 
	 * 
	 * @param date 
	 * @return 
	 */  
	 public static Date getLastDayOfWeek(Date date) {  
	 Calendar c = new GregorianCalendar();  
	 c.setFirstDayOfWeek(Calendar.MONDAY);  
	 c.setTime(date);  
	 c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday   
	 return c.getTime();  
	 }
/**
 * 获得今天本周第几天
 * @return
 */
	public static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK)-1; 		//因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}
	
	/**
	 * 获得上月第一天的日期
	 *@author 迟雪
	 *@version 1.0 
	 *  
	 */
	public static Date getPreviousMonthFirst(){  
		Calendar firstDate = Calendar.getInstance();
		firstDate.set(Calendar.DATE,1);//设为当前月的1号
		firstDate.add(Calendar.MONTH,-1);//减一个月，变为下月的1号
		//String str = DateTimeUtil.Date2String(lastDate.getTime(),2);
		return firstDate.getTime();  
	}

	/**
	 * 获得上月最后一天的日期
	 *@author 迟雪
	 *@version 1.0 
	 *  
	 */
	public static Date getPreviousMonthLast(){
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH,-1);//减一个月
		lastDate.set(Calendar.DATE, 1);//把日期设置为当月第一天 
		lastDate.roll(Calendar.DATE, -1);//日期回滚一天，也就是本月最后一天 
		//String str = DateTimeUtil.Date2String(lastDate.getTime(),2);
		return lastDate.getTime();  
	}
	/**
	 * 计算当月最后一天,返回字符串
	 *@author 迟雪
	 *@version 1.0 
	 * 
	 */
	public static Date getCurrentMonthLast(){  
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE,1);//设为当前月的1号
		lastDate.add(Calendar.MONTH,1);//加一个月，变为下月的1号
		lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天
		//String str = DateTimeUtil.Date2String(lastDate.getTime(),2);
		return lastDate.getTime();  
	}



	/**
	 * 获取当月第一天
	 *@author 迟雪
	 *@version 1.0 
	 * 
	 */
	public  static Date getCurrentMonthFirst(){  
		Calendar firstDate = Calendar.getInstance();
		firstDate.set(Calendar.DATE,1);//设为当前月的1号
		//String str = DateTimeUtil.Date2String(lastDate.getTime(),2);
		return firstDate.getTime();  
	}

	/**
	 *  获得下个月第一天的日期
	 *@author 迟雪
	 *@version 1.0 
	 *  
	 */
	public  static Date getNextMonthFirst(){
		String str = "";
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH,1);//减一个月
		lastDate.set(Calendar.DATE, 1);//把日期设置为当月第一天 
		//String str = DateTimeUtil.Date2String(lastDate.getTime(),2);
		return lastDate.getTime();  
	}
	/**
	 *  根据参数获得当前日期前几周的星期一的日期
	 * @param num
	 * @return
	 */
	public  static String getPreviousFewWeekday(int num) {
		weeks--;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + num*7 * weeks);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}
	
	
	/**
	 * 根据参数获得当前日期前几周的星期日的日期
	 * @param num
	 * @return
	 */
	public static String getPreviousFewWeekSunday(int num) {
		weeks--;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + num*weeks);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}
	
	
	/**
	 * 获得下个月最后一天的日期
	 *@author 迟雪
	 *@version 1.0 
	 *  
	 */
	public static Date getNextMonthLast(){
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH,1);//加一个月
		lastDate.set(Calendar.DATE, 1);//把日期设置为当月第一天 
		lastDate.roll(Calendar.DATE, -1);//日期回滚一天，也就是本月最后一天 
		//String str = DateTimeUtil.Date2String(lastDate.getTime(),2);
		return lastDate.getTime();  
	}
	
	/**
	 * 计算当前日期，前几个月最后一天,返回字符串
	 * @param date
	 * @param n
	 * @return
	 */
	public static String getDefaultDay(Date date,int n){  
		String str = "";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");    
		Calendar c = Calendar.getInstance();
	
		//lastDate.set(Calendar.DATE,1);//设为当前月的1号
		if(n!=0){
			c.add(Calendar.MONTH,n);//加一个月，变为下月的1号
		}
		c.add(Calendar.DATE,-1);//减去一天，变为当月最后一天
		//c.setTime(date);

		
		
		
		c.add(Calendar.MONTH,-1);//减一个月
		c.set(Calendar.DATE, 1);//把日期设置为当月第一天 
		c.roll(Calendar.DATE, -1);//日期回滚一天，也就是本月最后一天 
		
		
		str=sdf.format(c.getTime());
		
		
		
		
		
		
		return str;  
	}
	
	/**
	 * 	计算当前日期，前几个月第一天,返回字符串
	 * @param date
	 * @param amount
	 * @return
	 */
	public static String getFirstDay(Date date,int amount){  
		
			String str = "";
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");    
			Calendar lastDate = Calendar.getInstance();
			lastDate.add(Calendar.MONTH, amount);
			lastDate.set(Calendar.DATE,1);//设为当前月的1号
			//lastDate.setTime(date);
			str=sdf.format(lastDate.getTime());
			return str;  
	}
	/**
	 *   获得上年第一天的日期
	 *@author 迟雪
	 *@version 1.0 
	 *
	 */
	public static String getPreviousYearFirst(){
		Date date = new Date();
		SimpleDateFormat   dateFormat   =   new   SimpleDateFormat("yyyy");//可以方便地修改日期格式   
		String  years  = dateFormat.format(date); int years_value = Integer.parseInt(years);  
		years_value--;
		return years_value+"-1-1";
	}


	/**
	 * 获得上年最后一天的日期
	 *@author 迟雪
	 *@version 1.0 
	 *  
	 */
	public  static Date getPreviousYearLast(){
		weeks--;
		int yearPlus = getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE,yearPlus+MaxYear*weeks+(MaxYear-1));
		Date yearDay = currentDate.getTime();
		//String str = DateTimeUtil.Date2String(yearDay,2);
		return yearDay;
	}


	/**
	 * 获得本年有多少天
	 *@author 迟雪
	 *@version 1.0 
	 *  
	 */
	public static int getCurrentYearNumber(){
		Calendar cd = Calendar.getInstance();
		cd.set(Calendar.DAY_OF_YEAR,1);//把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR,-1);//把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);	
		return MaxYear;
	}

	public static int getYearPlus(){
		Calendar cd = Calendar.getInstance();
		int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);//获得当天是一年中的第几天
		cd.set(Calendar.DAY_OF_YEAR,1);//把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR,-1);//把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		if(yearOfNumber == 1){
			return -MaxYear;
		}else{
			return 1-yearOfNumber;
		}
	}

	/**
	 *  获得本年第一天的日期
	 *@author 迟雪
	 *@version 1.0 
	 * 
	 */
	public static Date getCurrentYearFirst(){
		int yearPlus = getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE,yearPlus);
		Date yearDay = currentDate.getTime();
		return yearDay;
	}

	/**
	 * 获得本年最后一天的日期
	 *@author 迟雪
	 *@version 1.0 
	 *  
	 */
	public static Date getCurrentYearLast(){
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR,0);//加一个年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		lastDate.roll(Calendar.DAY_OF_YEAR, -1);
		//String str = DateTimeUtil.Date2String(lastDate.getTime(),2);
		return lastDate.getTime();
	}
	/**
	 *  获得明年第一天的日期
	 *@author 迟雪
	 *@version 1.0 
	 * 
	 */
	public static Date getNextYearFirst(){
		weeks--;
		Calendar cd = Calendar.getInstance();
		cd.set(Calendar.DAY_OF_YEAR,1);//把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR,-1);//把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);	//获得本年有多少天
		int yearPlus = getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE,MaxYear-(yearPlus*weeks)+2);
		Date yearDay = currentDate.getTime();
		//String str = DateTimeUtil.Date2String(yearDay,2);
		return yearDay;

	}

	/**
	 * 获得明年最后一天的日期
	 *@author 迟雪
	 *@version 1.0 
	 *  
	 */
	public  static Date getNextYearLast(){
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR,1);//加一个年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		lastDate.roll(Calendar.DAY_OF_YEAR, -1);
		//String str = DateTimeUtil.Date2String(lastDate.getTime(),2);
		return lastDate.getTime();  
	}



	/**
	 *  获得某年某月某周星期几的日期
	 *@author 迟雪
	 *@version 1.0 
	 *@param year 年份 
	 *@param month 月份 
	 *@param weekOfMonth 这个月的第几周 
	 *@param dayOfWeek 星期几 
	 *
	 */
	public static Date getDate(int year,int month,int weekOfMonth,int dayOfWeek){ 
		Calendar c = Calendar.getInstance(); 
		//计算出 x年 y月 1号 是星期几 
		c.set(year, month-1, 1); 

		//如果i_week_day =1 的话 实际上是周日 
		int i_week_day = c.get(Calendar.DAY_OF_WEEK); 

		int sumDay = 0; 
		//dayOfWeek+1 就是星期几（星期日 为 1） 
		if(i_week_day == 1){ 
			sumDay = (weekOfMonth-1)*7 + dayOfWeek+1; 
		}else{ 
			sumDay = 7-i_week_day+1 + (weekOfMonth-1)*7 + dayOfWeek +1; 
		} 
		//在1号的基础上加上相应的天数 
		c.set(Calendar.DATE, sumDay); 
		//String str = DateTimeUtil.Date2String(c.getTime(),2);
		return c.getTime(); 
	} 

	/** 
	 * 获得某年某月某周星期几的日期 
	 * @param year 年份 
	 * @param month 月份 
	 * @param weekOfMonth 这个月的第几周 
	 * @param dayOfWeek 星期几 
	 * @return 
	 */ 
	public static String weekdatetodata(int year,int month,int weekOfMonth,int dayOfWeek){ 
		Calendar c = Calendar.getInstance(); 
		//计算出 x年 y月 1号 是星期几 
		c.set(year, month-1, 1); 

		//如果i_week_day =1 的话 实际上是周日 
		int i_week_day = c.get(Calendar.DAY_OF_WEEK); 

		int sumDay = 0; 
		//dayOfWeek+1 就是星期几（星期日 为 1） 
		if(i_week_day == 1){ 
			sumDay = (weekOfMonth-1)*7 + dayOfWeek+1; 
		}else{ 
			sumDay = 7-i_week_day+1 + (weekOfMonth-1)*7 + dayOfWeek +1; 
		} 
		//在1号的基础上加上相应的天数 
		c.set(Calendar.DATE, sumDay); 
		SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd"); 
		return sf2.format(c.getTime()); 
	} 


	/**
	 * 获取某年某月的最后一天
	 * @param year 年
	 * @param month 月
	 * @return 最后一天
	 */
	private static int getLastDayOfMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
				|| month == 10 || month == 12) {
			return 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
		return 0;
	}
	
	
	/**
	 * @author ranxing
	 * 获取指定日期字符串的   指定加减日期  的返回字符串
	 * @param strDate   传入日期字符串:20170201
	 * @param strDateFormat	对应传入字符串的format:"yyyyMMdd"  
	 * @param returnDateFormat	返回字符串的format:"yyyyMMddHHmmss"
	 * @param numA		GregorianCalendar的编号 1 2 3 5<br>
	 * gc.add(numA,numB);<br>
	 * gc.add(1,-1)表示年份减一. <br>
	 * gc.add(2,-1)表示月份减一.<br>
	 * gc.add(3.-1)表示周减一.<br>
	 * gc.add(5,-1)表示天减一.<br>
	 * @param numB		GregorianCalendar的加减数字
	 * @return   "20170125000000"
	 *
	 */
	public static String  strAdd2Str(String strDate,String strDateFormat,String returnDateFormat,int numA, int numB ){
		
		try {
			SimpleDateFormat formater = new SimpleDateFormat(strDateFormat);
			Date d = formater.parse(strDate);
			GregorianCalendar gc=new GregorianCalendar(); 
			gc.setTime(d); 
			gc.add(numA,numB); 
			String dayAfter=new SimpleDateFormat(returnDateFormat).format(gc.getTime()); 
				return dayAfter;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String localeEnglishToDefualt(String object ){
		//  s = "Tue Jul 12 00:00:00 GMT+08:00 2016";
	     SimpleDateFormat sf1 = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH); 
	     Date date = new Date();;
		try {
			
			date = sf1.parse(object);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	     SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf2.format(date);
	}
	
	
	public static void main(String[] args2) {
		System.out.println(11111);
	}

}
