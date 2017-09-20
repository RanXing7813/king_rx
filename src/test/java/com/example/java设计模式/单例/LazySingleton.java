package com.example.java设计模式.单例;

/**
 * 双重锁的懒汉式    在高并发中有可能 空指针
 * @author King_RX
 *
 */
public class LazySingleton {
	
	volatile private  static  LazySingleton instance = null;
	
   //	private LazySingleton(){}
	
	public  static LazySingleton getInstance(){

		if(instance == null){
			synchronized (LazySingleton.class) {
				if(instance == null){
					instance 
					= 
					new LazySingleton();
				}
			}
		}
		
		return instance;
	}
	 public static void main(String[] args) {
		 LazySingleton instance = LazySingleton.getInstance();
		 //instance = null;
		 LazySingleton instance2 = LazySingleton.getInstance();
		 System.out.println(instance);
		 System.out.println(instance2);
	 }
}
    
         