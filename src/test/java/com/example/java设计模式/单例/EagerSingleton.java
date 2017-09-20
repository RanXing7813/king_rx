package com.example.java设计模式.单例;

import java.util.ArrayList;
import java.util.List;

/**
 * 饿汉式单例模式   在类被加载的时候就创建了对象
 * @author King_RX
 *
 */
public class EagerSingleton {

	 private static final EagerSingleton instance = new EagerSingleton ();
	 
	 public EagerSingleton(){}
	 
	 public static  EagerSingleton getInstance(){
		 
		 return instance;
	 }
	 
	 
	 public static void main(String[] args) {
		 EagerSingleton instance = EagerSingleton.getInstance();
		 EagerSingleton instance2 = EagerSingleton.getInstance();
		 System.out.println(instance);
		 System.out.println(instance2);
		 for (int i = 0; i <10; i++) {
			 List list =new ArrayList();
			 //list.add(i);
			System.out.println(list.hashCode());
		}
	 }
}
