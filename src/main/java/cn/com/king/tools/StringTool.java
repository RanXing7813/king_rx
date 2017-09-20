package cn.com.king.tools;

import java.util.Date;
import java.util.UUID;

/**
 * @author King_RX
 *  string type utils
 */
public class StringTool {
	
	 	private static final int INDEX_NOT_FOUND = -1;    
	    private static final String EMPTY = "";  
	
	
	public static String  null2Empty(Object obj){
		if( obj == null ){
			return EMPTY;
		}
		return obj.toString();
	}
	
	public static String  getUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	
	public static void main(String[] args) {
		System.out.println(null2Empty("ss"));
		
	}
	
	

}
