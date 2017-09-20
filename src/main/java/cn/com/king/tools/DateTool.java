package cn.com.king.tools;

import java.security.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import oracle.sql.TIMESTAMP;

/**
 * @author King_RX
 *
 */
public class DateTool {
	
	private static final String  deafultSimpleDateFormat = "yyyy-MM-dd HH:mm:ss";
	private static final String EMPTY = ""; 
	
	
	/**
	 * 默认时间转 strDate   eg：Tue Jul 12 00:00:00 GMT+08:00 2016
	 * @param strDate 日期字符串
	 * @param num 日期格式编号
	 * 
	 * @return String ： yyyy-MM-dd HH:mm:ss
	 */
	public static String localeEnglishToDefualt(Object strDate ){
		
		 if(strDate==null || strDate.toString().isEmpty() ) 
			 return EMPTY;
		
	     SimpleDateFormat sf1 = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH); //  s = "Tue Jul 12 00:00:00 GMT+08:00 2016";
	     Date date = new Date();
		try {
			
			date = sf1.parse(strDate.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	     SimpleDateFormat sf2 = new SimpleDateFormat(deafultSimpleDateFormat);
		return sf2.format(date);
	}
	
	/**
	 * 默认时间转 strDate   eg：Tue Jul 12 00:00:00 GMT+08:00 2016
	 * @param strDate 日期字符串
	 * @param num 日期格式编号
	 * <br>	   	switch (num) {
<br>		case 1:
<br>			return "yyyy-MM-dd HH:mm:ss";
<br>		case 11:
<br>			return "yyyy/MM/dd HH/mm/ss";
<br>		case 2:
<br>			return "yyyy-MM-dd";
<br>		case 22:
<br>			return "yyyy/MM/dd";
<br>
<br>		default:
<br>			return "yyyy-MM-dd HH:mm:ss";
<br>			
<br>		}
	 * @return
	 */
	public static String localeEnglishToFormat(String strDate , int num){
		
		 if(strDate==null || strDate.toString().isEmpty() ) 
			 return EMPTY;
		 
	     SimpleDateFormat sf1 = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH); //  s = "Tue Jul 12 00:00:00 GMT+08:00 2016";
	     Date date = new Date();
		try {
			date = sf1.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	
	     SimpleDateFormat sf2 = new SimpleDateFormat(formatType(num));
		return sf2.format(date);
	}
	
	/**
	 * timestamp数字转strDate   eg: 1504843369000
	 * @param timestamp  eg: 1504843369000
	 * @return           eg: 2017/9/8 12:2:49
	 */
	public static String timestampToDate(long timestamp){
									 
		String date = new java.text.SimpleDateFormat(deafultSimpleDateFormat).format(new java.util.Date(timestamp));  
		return date;
	}
	
	/**
	 * 比较2个日期 yyyy-MM-dd  相隔天数
	 * @param smdate
	 * @param bdate
	 * @return 正整数
	 */
	public static int daysBetween(Date smdate,Date bdate) { 
		
		  return (int) Math.abs(((smdate.getTime() / 86400000L) - (bdate.getTime() / 86400000L))); 
	} 
	
	public static void main(String[] args) {
		
		//System.out.println(localeEnglishToDefualt(new TIMESTAMP()));
		//System.out.println(localeEnglishToDefualt(dateTypeOf(new TIMESTAMP())));
		//1504843369544
		
		System.out.println(daysBetween(new java.util.Date("2017/1/1"),new java.util.Date("2017/1/3")));
	}
	
	
	
	
	public static String dateTypeOf(Object obj){
		return "" ;
	}
	
	/**
	 * format类型
	 * @param num
	 * @return string
	 * case 1:
			return "yyyy-MM-dd HH:mm:ss";
		case 11:
			return "yyyy/MM/dd HH/mm/ss";
		case 2:
			return "yyyy-MM-dd";
		case 22:
			return "yyyy/MM/dd";
	 */
	public static String formatType(int num){
		switch (num) {
		case 1:
			return "yyyy-MM-dd HH:mm:ss";
		case 11:
			return "yyyy/MM/dd HH/mm/ss";
		case 2:
			return "yyyy-MM-dd";
		case 22:
			return "yyyy/MM/dd";

		default:
			return deafultSimpleDateFormat;
			
		}
	}
	
	
	

}
