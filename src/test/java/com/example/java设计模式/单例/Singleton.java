package com.example.java设计模式.单例;


/**
  饿汉式单例类不能实现延迟加载，不管将来用不用始终占据内存；
  懒汉式单例类线程安全控制烦琐，而且性能受影响。
  可见，无论是饿汉式单例还是懒汉式单例都存在这样那样的问题，
  有没有一种方法，能够将两种单例的缺点都克服，而将两者的优点合二为一呢？
  答案是：Yes！
  下面我们来学习这种更好的被称之为Initialization on Demand Holder (IoDH)的技术。
    在IoDH中，我们在单例类中增加一个静态(static)内部类
    （其缺点是与编程语言本身的特性相关，很多面向对象语言不支持IoDH）。
 * @author King_RX
 *
 */
public class Singleton {
	
    private  Singleton(){}
     
      private static class HolderClass{
             private static final Singleton  instance = new Singleton();
      }
     
      public static Singleton getInstance(){
             return HolderClass.instance;
      }
      
      
      
      
     
      public  static void main(String args[]){
             Singleton  s1, s2;
             s1 = Singleton.getInstance();
             s2  = Singleton.getInstance();
             System.out.println(s1==s2);
             
             int x = 1;
             int y = x;
                 x = 0;
             System.out.println(y);
             
      }
}
