package com.example.java设计模式.观察者模式_中介者模式;


public class Product implements Cloneable {
	
	/**产品名称*/
	private String name;
	
	/**是否可以属性变更*/
	private boolean canChanged = false ;
	
	/**产生一个新产品*/
	public Product(ProductManager manager, String _name){
		/**允许创建一个新产品*/
//		if(manager.i){
//			
//		}
	}

	public Product clone(Product p) throws CloneNotSupportedException   {
		//产生克隆事件
		return (Product) p.clone();
	}
	
	/**克隆一个产品*/
	
	
}
