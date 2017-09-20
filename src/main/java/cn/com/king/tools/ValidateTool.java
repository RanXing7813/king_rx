package cn.com.king.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateTool {

	/**
	 * isMobiPhoneNum:(判断手机号码是否合法). <br/>
	 * @author zhangf
	 * @param numArray
	 * @return
	 * @since JDK 1.6
	 */
	private static boolean isMobiPhoneNum(String[] numArray){
		boolean flag = false;
        String regex = "^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";  
        Pattern p = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);   //CASE_INSENSITIVE  忽略大小写
        if(numArray != null && numArray.length >0){
        	for(String str:numArray){
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
	
//	public static void main(String[] args) {
//		System.out.println(isMobiPhoneNum(new String[]{"151712686046","15171268604"} ));
//	}
	
	  private  ValidateTool()
	  {
	         }
	        
	         private static class HolderClass
	         {
	                private final static ValidateTool  instance = new ValidateTool();
	         }
	        
	         public static ValidateTool getInstance()
	         {
	                return HolderClass.instance;
	         }
	        
	         public  static void main(String args[])
	         {
	                ValidateTool  s1, s2;
	                s1 = ValidateTool.getInstance();
	                s2  = ValidateTool.getInstance();
	                System.out.println(s1==s2);
	         }
	
}
