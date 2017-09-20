package cn.com.taiji.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBConnection {
	
	//门户数据表库url、username、password
	private static String RK_DB_URL = LoadPropertiesDataUtils.getValue("RK_DB_URL");
	private static String RK_DB_USERNAME = LoadPropertiesDataUtils.getValue("RK_DB_USERNAME");
	private static String RK_DB_PASSWORD = LoadPropertiesDataUtils.getValue("RK_DB_PASSWORD");
	
	//落地数据库url、username、password
	private static  String CENTER_DB_URL = LoadPropertiesDataUtils.getValue("CENTER_DB_URL");
	private static  String CENTER_DB_USERNAME = LoadPropertiesDataUtils.getValue("CENTER_DB_USERNAME");
	private  static String CENTER_DB_PASSWORD = LoadPropertiesDataUtils.getValue("CENTER_DB_PASSWORD");
	
	/**
	 * MySQL连接
	 * 76/242库连接
	 */
	public static Connection getMySQLConn(){
		Connection conn=null;
		try{   
			//加载MySql的驱动类   
		    Class.forName("com.mysql.jdbc.Driver");  
		    conn = DriverManager.getConnection(RK_DB_URL, RK_DB_USERNAME, RK_DB_PASSWORD);
		}catch(Exception e){   
		    e.printStackTrace();   
	    }
		return conn;
	}
	
	/**
	 * Oracle连接
	 * 166库连接
	 */
	public static Connection getCenterdbConn(){
		Connection conn=null;
		try{   
			//加载MySql的驱动类   
			Class.forName("oracle.jdbc.driver.OracleDriver");  
		    conn = DriverManager.getConnection(CENTER_DB_URL,CENTER_DB_USERNAME,CENTER_DB_PASSWORD);
		}catch(Exception e){   
		    e.printStackTrace();   
	    }
		return conn;
	}
	
	/*public static void main(String[] args){
		System.out.println(RK_DB_URL);
		System.out.println(RK_DB_USERNAME);
		System.out.println(RK_DB_PASSWORD);
		
		System.out.println(CENTER_DB_URL);
		System.out.println(CENTER_DB_USERNAME);
		System.out.println(CENTER_DB_PASSWORD);
	}*/
	
}
