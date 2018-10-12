package cn.com.example;


/** 
* @ClassName: ProductEventType 
* @Description: 事件类型定义类
* @author ranxing
* @date 2018年1月27日 上午11:46:37 
*  
*/
public enum ProductEventType {
	
	/**新建一个产品*/
	NEW_PRODUCT(1),
	
	/**删除一个产品*/
	DEL_PRODUCT(2),
	
	/**修改一个产品*/
	EDIT_PRODUCT(3),
	
	/**克隆一个产品*/
	CLONE_PRODUCT(4);
	
	private int value = 0 ; 
	
	private ProductEventType(int _value){
		this.value = _value;
	}
	public int getValue(){
		return this.value;
	}
	
}
