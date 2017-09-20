package com.example.java设计模式.单例;

import java.util.HashMap;
//import org.testng.annotations.AfterTest;
public class Test {
	  private long count=0;

	    public long getCount(){
	        return count;
	    }

	//    @org.testng.annotations.Test(invocationCount=20000,threadPoolSize=1000) 
	    public void service() {
	        	++count;

	           Thread thread1 = new Thread(new SyncThread(), "SyncThread"+count);
//			   Thread thread2 = new Thread(new SyncThread(), "SyncThread2");
//			   thread1.start();
			   thread1.start();
	    }

	 //   @AfterTest
	    public void endService(){
	        System.out.println("count value{}:"+count);
	    }  
}
