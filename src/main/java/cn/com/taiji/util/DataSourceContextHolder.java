package cn.com.taiji.util;

public class DataSourceContextHolder {  
  
    private static final ThreadLocal contextHolder=new ThreadLocal();  
      
    public static void setDataSourceType(String dataSourceName){  
        contextHolder.set(dataSourceName);  
    }  
      
    public static String getDataSourceName(){  
        return (String) contextHolder.get();  
    }  
      
    public static void clearDataSourceType(){  
        contextHolder.remove();  
    }  
      
}