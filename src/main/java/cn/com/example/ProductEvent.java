package cn.com.example;

import java.util.Observable;


/** 
* @ClassName: ProductEvent 
* Observable 被观察这
* @Description: 产品事件
* @author ranxing
* @date 2018年1月27日 上午11:48:06 
*  
*/
public class ProductEvent extends Observable {
	/**事件源*/
	private Product souce;
	
	/**事件类型*/
	private ProductEventType type;
	
	/**默认 事件类型为新建*/
	public ProductEvent(Product p){
		
	}
	
	/**事件源 及 事件类型*/
	public ProductEvent(Product p, ProductEventType _type){
		this.souce = p ;
		this.type = _type;
		//事件触发
		notifyEventDispatch();
	}
	
	/**通知事件处理中心*/
	private void notifyEventDispatch(){
		//添加一个事件观察者
		super.addObserver(EventDispatch.getEventDispatch());
		//该表状态为true
		super.setChanged();
		//调用 Observer 时间观察者的 update方法,  事件触发;
		super.notifyObservers(souce);
	}
	
	
}
