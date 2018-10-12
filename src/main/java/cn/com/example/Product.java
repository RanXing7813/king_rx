package cn.com.example;


public class Product implements Cloneable {
	
	/**产品名称*/
	private String name;
	
	/**是否可以属性变更*/
	private boolean canChanged = false ;
	
	/**产生一个新产品*/
	public Product(ProductManager manager, String _name){
		/**允许创建一个新产品*/
		if(manager.isCreateProduct()){
			canChanged = true;
			this.name = _name;
		}
	}

	/**复写clone方法*/
	@Override
	public Product clone() {
		Product p = null;
		try {
			p = (Product) super .clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return p;
	}

	
	
	
	
	//getter setter...
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
