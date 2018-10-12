package cn.com.king.listing;


import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.com.king.annotation.NeedntLogin;


public class SessionInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		//String path = request.getRequestURL().toString().toLowerCase();
		
		NeedntLogin needntLogin = ((HandlerMethod) handler).getMethodAnnotation(NeedntLogin.class);
		
		//String method = request.getMethod();
	 
		boolean beFilter = true;
		if (needntLogin != null) {
			if (needntLogin.validate() == true)
				beFilter = false;
		}
		
	       String type = request.getHeader("X-Requested-With")==null?"":request.getHeader("X-Requested-With");// XMLHttpRequest   

		if (beFilter) {// 需要登陆验证
			Object obj = request.getSession().getAttribute("user");
			if (null == obj) {// 登陆session过期
				  if (StringUtils.equals("XMLHttpRequest", type)) {// 处理ajax请求  
					 response.setHeader("REDIRECT", "REDIRECT");//告诉ajax这是重定向  
			         response.setHeader("CONTEXTPATH", "/index");//重定向地址      /theme/page/index.js全局js
                     response.setStatus(HttpServletResponse.SC_FORBIDDEN);  
                     return false;
				  }else{
					 response.sendRedirect("/index");
					 return false;
				  }
			}
		}
//		return true;
		return super.preHandle(request, response, handler);
	}
	
	/**
	 * 
	 * 功能名称：登录页面 参数： 返回值：StringBuilder 作者: zhongdd 创建时间: 2017年4月26日 下午12:58:43
	 * 说明:
	 */
	public StringBuilder loginHtml(String method) {
		method = getMethod(method);
		StringBuilder builder = new StringBuilder();
		builder.append("<!DOCTYPE html>");
		builder.append("<html xmlns:th=\"http://www.thymeleaf.org\" lang=\"en\" >");
		builder.append("<head>");
		builder.append("<meta charset=\"UTF-8\" />");
		builder.append("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"/>");
		builder.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
		builder.append("</head>");
		builder.append("<body>");
		builder.append("</body>");
		builder.append("<script type=\"text/javascript\" src=\"../theme/portals/js/jquery-1.11.2.min.js\"></script>");
		builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
		builder.append(" window.parent.location.href='/index';");
		builder.append("</script>");
		builder.append("</html>");
		return builder;
	}
	
	public StringBuilder loginTip() {
		StringBuilder builder = new StringBuilder();
		builder.append("<script type=\"text/javascript\" src=\"../theme/portals/js/jquery-1.11.2.min.js\"></script>");
		builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
		builder.append(" window.parent.location.href='/index';");
		builder.append("</script>");
		return builder;
	}
	
	/**
	 * 
	 * 功能名称：参数过来 防sql注入 参数： 返回值：boolean 作者: zhongdd 创建时间: 2017年4月26日 下午12:59:09
	 * 说明:
	 */
	public boolean sql_inj(String str) {
		String inj_str = "'|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|+|,";

		String[] inj_stra = inj_str.split("\\|");
		for (int i = 0; i < inj_stra.length; i++) {
			if (str.indexOf(inj_stra[i]) >= 0) {
				return true;
			}
		}
		return false;
	}
	
	public String getMethod(String method){
		switch (method) {
		  	  case "dataService_Index" :
			  	  return "dataService_Index";
			  case "serviceMenu_Index" :
				  return "serviceMenu_Index"; 
			  case "projectMenu_Index" :
				  return "projectMenu_Index";
			  case "runningMonitor_Index" :
				  return "runningMonitor_Index";
			  case "system_Index" :
				  return "system_Index";
			  default :
				  return "nodeMonitor_Index";
		}
	}
}
