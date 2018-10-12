package cn.com.example;



/** 
* @ClassName: ProductManager 
* @Description: 产品工厂类
* @author ranxing
* @date 2018年1月27日 上午11:01:27 
*  
*/
public class ProductManager {
	
	/**是否可以创建一个产品*/
	boolean isPermittedCreate = false;
	
	/**建立一个产品*/
	public Product createProduct(String name){
			/**修改权限允许新建*/
			isPermittedCreate  = true;
			Product p = new Product(this, name);
			return p;
	}
	
	/**废弃一个产品*/
	public void abandonProduct(Product p){
			p = null;
	}
	
	/**修改一个产品*/
	public void editProduct(Product p, String name){
			/**修改后的产品  这里只修改名字*/
			p.setName(name);
	}
	
	/**获得是否可以创建一个产品,Product中会用到
	 * 这种一个对象只能有固定的对象初始化的方法叫叫做单来源调用(Single Call)
	 * 这样2个对象就是组合关系了, 两者局用相同的生命周期*/
	public boolean isCreateProduct(){
		return isPermittedCreate;
	}
	
	/**克隆一个产品*/
	public Product clone(Product p){
		/**调用产品中的复制方法   使用原型模式*/
		return p.clone();
	}
}
