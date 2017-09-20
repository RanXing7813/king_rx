/**
 * Project Name:reconcile-api
 * File Name:MessageInterface.java
 * Package Name:cn.com.taiji.comm.tools
 * Date:2017年5月22日上午10:34:43
 * Copyright (c) 2017, chenzhou1025@126.com All Rights Reserved.
 *
*/
package cn.com.taiji.util;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
/**
 * ClassName:MessageInterface <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017年5月22日 上午10:34:43 <br/>
 * @author   zhangf
 */
public class SendMessageTools {
	
	private static final String Account = "ctgc01";
	private static final String PWD = "ctgc01#gdic";
	private static final String FORMAT = "yyyy-MM-dd HH:mm";
	private static final String URL = "http://oasms.gd.gov.cn/oasms/SMInterface.aspx";
	
	private BufferedReader r = null; 
	/**
	 * sendMessage:(这里用一句话描述这个方法的作用). <br/>
	 * @author zhangf
	 * @param userName:发送人
	 * @param phoneArray:发送手机号码
	 * @param textstr:发送内容
	 * @param sendTime:发送时间
	 */
	public String sendMessage(String[] phoneArray,String textstr,String sendTime){
		String returnMessage = "";
		//校验手机号码
		if(phoneArray != null && phoneArray.length >0 ){
			if(isMobiPhoneNum(phoneArray) == false){
				returnMessage = "请输入合法手机号码！";
			}else{
				//校验短信长度
				if(textstr != null && textstr.trim().length() > 0 && textstr.trim().length() < 500){
					//校验发送时间
					if(isValidDate(sendTime) == false){
						returnMessage = "请输入形如2017-01-01 11:00的有效日期！";
					}else{
						try
						{	//http://oasms.gd.gov.cn/oasms/SMInterface.aspx
							URL u=new URL(URL);
							HttpURLConnection h=(HttpURLConnection)u.openConnection();
							h.setDoOutput(true);
							h.setRequestMethod("POST"); //设置为post请求
							h.setRequestProperty("Content-Type","application/x-www-form-urlencoded"); //设置发送数据的类型
							OutputStream out=h.getOutputStream();
							String strMobile = "";
							for(String str:phoneArray){
								strMobile += str.trim()+"*";
							}
							strMobile = strMobile.substring(0, strMobile.length()-1);
							String strMsg = URLEncoder.encode(textstr);
							String postData = "";
							
							//发送短信
							if(sendTime != null && sendTime.length() >0){
								postData = "Account="+Account+"&PWD="+PWD+"&Msg1="+strMsg+"&Mobile1="+strMobile+"&SendTime="+sendTime;
							}else{
								postData = "Account="+Account+"&PWD="+PWD+"&Msg1="+strMsg+"&Mobile1="+strMobile;
							}
							out.write(postData.getBytes());
							r = new BufferedReader(new InputStreamReader(h.getInputStream())); 
							String line = r.readLine();
							System.out.println(line);
							if(line != null && "OK".equals(line)){
								returnMessage = "SUCCESS";
							}else{
								returnMessage = "提交识别处理,请稍后！";
							}
						}
						catch(Exception ex){
							returnMessage = "网络故障等错误处理,请联系管理员！";
						}finally{
							if (r != null){ 
								try { 
									r.close(); 
									r = null; 
								} catch (IOException e) { 
									e.printStackTrace(); 
								} 
							}
						}
					}
				}else if(textstr != null && textstr.trim().length() > 500){
					returnMessage = "短信内容过长，请输入小于500字的短信内容！";
				}else{
					returnMessage = "短信内容不能为空，请输入小于500字的短信内容！";
				}
			}
		}else{
			returnMessage = "请输入需要发送的手机号码！";
		}
		return returnMessage;
	}
	
	
	/**
	 * isMobiPhoneNum:(判断手机号码是否合法). <br/>
	 * @author zhangf
	 * @param telNumArray
	 * @return
	 * @since JDK 1.6
	 */
	private boolean isMobiPhoneNum(String[] telNumArray){
		boolean flag = false;
        String regex = "^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";  
        Pattern p = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);  
        if(telNumArray != null && telNumArray.length >0){
        	for(String str:telNumArray){
        		if(str != null && !"".equals(str)){
        			Matcher m = p.matcher(str);
            		flag = m.matches();
            		if(flag == false){
            			break;
            		}
        		}
        	}
        }
        return flag;  
    }  
	
	
	
	/**
	 * isValidDate:(判断时间日期是否合法). <br/>
	 * @author zhangf
	 * @param str
	 * @return
	 */
	private  boolean isValidDate(String time) {
		boolean convertSuccess = true;
		SimpleDateFormat format = new SimpleDateFormat(FORMAT);
		try {
			if(time != null && time.length() >0){
				format.setLenient(false);
				format.parse(time);
			}
		} catch (ParseException e) {
			convertSuccess = false;
		} 
		return convertSuccess;
	}
	
	
	static public void main(String args[]) 
	{	
		try
		{	//http://oasms.gd.gov.cn/oasms/SMInterface.aspx
			URL u = new URL("http://oasms.gd.gov.cn/oasms/SMInterface.aspx");
			HttpURLConnection h=(HttpURLConnection)u.openConnection();
			h.setDoOutput(true);
			h.setRequestMethod("POST"); //设置为post请求
			h.setRequestProperty("Content-Type","application/x-www-form-urlencoded"); //设置发送数据的类型
			OutputStream out=h.getOutputStream();
			
			String strMobile =  "18612258910";
			String strMsg = URLEncoder.encode("中文 !@#$%^&*()123");
			System.out.println(strMsg);
			String postData = "Account=ctgc01&PWD=ctgc01#gdic&Msg1="+strMsg+"&Mobile1="+strMobile+"&SendTime=2016-01-01";
			out.write(postData.getBytes());
	
			BufferedReader   r   =   new   BufferedReader(new   InputStreamReader(h.getInputStream())); 
			String   line =  r.readLine();
			System.out.println(line);
			if(line=="OK" )
			{
				//提交成功处理....
			}
			else
			{
				//提交识别处理....
			}
		}
		catch(Exception ex)		
		{
			//网络故障等错误处理.....
		}
	}
}

