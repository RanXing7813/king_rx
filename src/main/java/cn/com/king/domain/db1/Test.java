package cn.com.king.domain.db1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import cn.com.king.web.service.login.LoginService;

public class Test {
		public static void testSpringAOP(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/*.xml");
        
        //PortalsIndexController portalsIndexController = (PortalsIndexController)ctx.getBean("portalsIndexController");
        ModelAndView ss = new ModelAndView();
        Object models = new Object();
        Model model =    null; 
        
        
      //  portalsIndexController.homePage((Model)model, "12344");
		}
	public static void main(String[] args) {
	testSpringAOP();
	}
}
