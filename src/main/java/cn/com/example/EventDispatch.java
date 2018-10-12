package cn.com.example;

import java.util.Observable;
import java.util.Observer;


/** 
* @ClassName: EventDispatch 
* @Description: 事件观察者 
* @author ranxing
* @date 2018年1月27日 下午12:00:01 
*  
*/
public class EventDispatch implements Observer {

	/**单利模式*/
	private  final static EventDispatch dispatch = new EventDispatch();
	
	/**不允许产生新的实例*/
	private EventDispatch(){
		
	}
	
	/**获取单个实例*/
	public static EventDispatch getEventDispatch(){
		return dispatch ;
	}
	
	/**事件触发*/
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

}
