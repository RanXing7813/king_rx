package cn.com.king.web.action.log;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

	
	@Component("BeanDefineConfigue")
	public class BeanDefineConfigue implements
	ApplicationListener<ContextRefreshedEvent> {//ContextRefreshedEvent为初始化完毕事件，spring还有很多事件可以利用

	// @Autowired
	// private IRoleDao roleDao;


	/**
	* 当一个ApplicationContext被初始化或刷新触发
	*/
//	@Override
//	public void onApplicationEvent(ContextRefreshedEvent event) {
//	// roleDao.getUserList();//spring容器初始化完毕加载用户列表到内存
//	System.out.println("spring容易初始化完毕================================================");
//	}

	    @Override  
	      public void onApplicationEvent(ContextRefreshedEvent event) {  
	        if(event.getApplicationContext().getParent() == null){//root application context 没有parent，他就是老大. 
	             //需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。  
	        	System.out.println("spring容易初始化完毕================================================");
	            LogUtils logUtils= new LogUtils();
	            logUtils.loadConfig("logkey.conf");
	        	
	        	
	        }  
	      } 

	}
