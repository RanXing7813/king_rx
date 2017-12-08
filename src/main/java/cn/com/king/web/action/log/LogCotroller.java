package cn.com.king.web.action.log;

import java.lang.reflect.Field;


import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;

import cn.com.king.dto.UserDto;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

@Aspect
@Component("LogCotroller")
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class LogCotroller {
//	private LogUtils logUtils;
//	public LogUtils getLogUtils() {
//		return logUtils;
//	}
//
//
//	public void setLogUtils(LogUtils logUtils) {
//		this.logUtils = logUtils;
//	}

	private static final Logger logger = LoggerFactory.getLogger(LogCotroller.class);
	  private String requestPath = null ; // 请求地址  
	    private String userName = "" ; // 用户名  
	    private Map<?,?> inputParamMap = null ; // 传入参数  
	    private Map<String, Object> log = null; // 存放输出结果  
	    private long startTimeMillis = 0; // 开始时间  
	    private long endTimeMillis = 0; // 结束时间  
	    private UserDto user = null;
	    private HttpServletRequest request = null;
	    
	
	  private static String[] types = { "java.lang.Integer", "java.lang.Double",  
	            "java.lang.Float", "java.lang.Long", "java.lang.Short",  
	            "java.lang.Byte", "java.lang.Boolean", "java.lang.Char",  
	            "java.lang.String", "int", "double", "long", "short", "byte",  
	            "boolean", "char", "float" };  
																//&& !execution(* cn.com.king.web.action.log..*(..))
	@Pointcut("execution(* cn.com.taiji.web.action..*.*(..)) ")//and !execution(* cn.com.taiji.web.action.NotNeedLoginController.*(..))
	public void pointcut(){};
	
	
    @Before(value = "pointcut()")
    public void befores(JoinPoint joinpoint  ){  
            joinpoint.getArgs();//此方法返回的是一个数组，数组中包括request以及ActionCofig等类对象  
            System.out.println("被拦截方法调用之前调用此方法，输出此语句");  
            
               RequestAttributes ra = RequestContextHolder.getRequestAttributes();
	           ServletRequestAttributes sra = (ServletRequestAttributes) ra;
	          // HttpServletRequest
	           request = sra.getRequest();
	      //  UserDto
	        user =   (UserDto) request.getSession().getAttribute("user");
	        Object obj =request.getParameter("fileName");
	        System.out.println("方法调用前: " + obj);
	     //   user = (UserDto)request.getSession().getAttribute(SecurityConstant.CURRENT_LOGIN_USER);
	        startTimeMillis = System.currentTimeMillis(); //记录方法开始执行的时间  
          //  HttpServletRequest req = (HttpServletRequest)request;
            String requestUrl = request.getRequestURI();
           // Map f
            log = LogUtils.parseUrl(requestUrl);
            if (log != null) {
            log.put("LOG_IP", request.getRemoteAddr());
            log.put("LOG_URL", requestUrl);
            log.put("LOGIN_USER", getCurrentUserID(request));
            log.put("USER_NAME", getCurrentUserName(request));
            log.put("LOG_DATE", getCurrDate());
            log.put("OPER_PARAM", getRequestParameterStrList(request));
            }
	        logger.info("请求开始, 各个参数, log: {}, startTimeMillis: {}",log ,startTimeMillis);

            try {
              //LogUtils.insertlog(log);
            } catch (Exception e) {
              System.out.println("日志记录失败！");
            }
//            
//          //此方法返回的是一个数组，数组中包括request以及ActionCofig等类对象
////	        Object[] args = joinpoint.getArgs();
////	        System.out.println("目标参数列表：");
////	        if (args != null) {
////	            for (Object obj : args) {
////	                System.out.println(obj + ",");
////	            }
////	            System.out.println();
////	     //       System.out.println("执行结果是：" + returnObj);
////	         }
    }  
    
    
	@After(value = "pointcut()"  )
	public void afters(JoinPoint joinpoint){  
		endTimeMillis = System.currentTimeMillis(); //记录方法开始执行的时间  
//                       try {
//					new Thread().sleep(3000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
	              System.out.println("被拦截方法调用之后调用此方法，输出此语句");  
	  	        logger.info("请求开始, 各个参数, log: {}, endTimeMillis: {}",log ,endTimeMillis);

	              //此方法返回的是一个数组，数组中包括request以及ActionCofig等类对象
//	    	        Object[] args = joinpoint.getArgs();
//	    	        System.out.println("目标参数列表：");
//	    	        if (args != null) {
//	    	            for (Object obj : args) {
//	    	                System.out.println(obj + ",");
//	    	            }
//	    	            System.out.println();
//	    	           / System.out.println("执行结果是：" + returnObj);
//	    	         }
	}  
	
	
	//配置环绕通知,使用在方法aspect()上注册的切入点
		@Around(value = "pointcut()")
		public Object  arounds(ProceedingJoinPoint jp)
			throws java.lang.Throwable  
		    {  
		        System.out.println("执行目标方法之前，模拟开始事务...");  
		        // 获取目标方法原始的调用参数  
		        Object[] args = jp.getArgs();  
		        if(args != null && args.length > 1)  
		        {  
		            // 修改目标方法的第一个参数  
		           // args[0] = "【增加的前缀】" + args[0];  
		        }  
		        // 以改变后的参数去执行目标方法，并保存目标方法执行后的返回值  
		        Object rvt =   jp.proceed();  
		        System.out.println("执行目标方法之后，模拟结束事务...");  
		        // 如果rvt的类型是Integer，将rvt改为它的平方  
//		        if(rvt != null && rvt instanceof Integer)  
//		            rvt = (Integer)rvt * (Integer)rvt;  
		        return rvt;  
		    }  
	
		//配置抛出异常后通知,使用在方法aspect()上注册的切入点
		@AfterThrowing(pointcut="pointcut()", throwing="ex")
		public void afterThrow(JoinPoint joinPoint, Exception ex){
			if(logger.isInfoEnabled()){
				logger.info("afterThrow " + joinPoint + "\t" + ex.getMessage());
			}
		}
	
	@AfterReturning(value="pointcut()",returning="val")
		public void endRecordLog(JoinPoint joinPoint,Object val)
	       throws Throwable {  
		
		//ServletWebRequest  f =( ServletWebRequest) RequestContextHolder.getRequestAttributes();
		
    	//HttpServletRequest request = f.get ////(ServletRequestAttributes) 

	           System.out.println("------afters之后---------");  
//	           String classType = joinPoint.getTarget().getClass().getName();  
//	           Class<?> clazz = Class.forName(classType);  
//	           String clazzName = clazz.getName();  
//	           String clazzSimpleName = clazz.getSimpleName();  
//	           String methodName = joinPoint.getSignature().getName();  
//	             
//	           String[] paramNames = getFieldsName(this.getClass(), clazzName, methodName);  
//	             
//	           String logContent = writeLogInfo(paramNames, joinPoint);  
//	             
//	           Logger logger = LoggerFactory.getLogger(clazzName);  
//	           logger.info("clazzName: "+clazzName+", methodName:"+methodName+", param:"+ logContent);  
	           
	           RequestAttributes ra = RequestContextHolder.getRequestAttributes();
	           ServletRequestAttributes sra = (ServletRequestAttributes) ra;
	           HttpServletRequest request = sra.getRequest();

	           String url = request.getRequestURL().toString();
	           String method = request.getMethod();
	           String uri = request.getRequestURI();
	           String queryString = request.getQueryString();
	           logger.info("请求开始, 各个参数, url: {}, method: {}, uri: {}, params: {}", url, method, uri, queryString);

	           // result的值就是被拦截方法的返回值
	           Object result = val;
	           Gson gson = new Gson();
	      //     logger.info("请求结束，controller的返回值是 " + gson.toJson(result));
	             
	       }  
	
	public static void main(String[] args) {
		//String pwd = StringUtils.trimToEmpty("123");
		//logger.info("请求结束，controller的返回值是 "+pwd);
	}
	
	
	
	
	
	
	
	
	
	
	
	 public static java.util.Date getCurrDate()
	  {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    try {
	      return dateFormat.parse(dateFormat.format(new java.util.Date()));
	    } catch (ParseException e) {
	      e.printStackTrace();
	    }
	    return null;
	  }
	  public String getCurrentUserID(HttpServletRequest request)
	  {
		  UserDto userInfo = getCurrUserInfo(request);
	    String userId = "";
	    if (!(StringTooles.isNullOrEmpty(userInfo))) {
	      userId = StringTooles.parseStr(userInfo.getId());
	    }
	    return userId;
	  }

	  public String getCurrentUserName(HttpServletRequest request)
	  {
		  UserDto userInfo = getCurrUserInfo(request);
	    String loginName = "";
	    if (!(StringTooles.isNullOrEmpty(userInfo)))
	      loginName = StringTooles.parseStr(userInfo.getUserName());
	    else {
	      loginName = "匿名";
	    }
	    return loginName;
	  }

	  public UserDto getCurrUserInfo(HttpServletRequest request)
	  {
	    return ((UserDto)request.getSession().getAttribute("user"));
	  }

	  private String getRequestParameterStrList(HttpServletRequest request)
	  {
	    Map<String, String[]> params = request.getParameterMap();
	    String paramlist = "";
	    for (String key : params.keySet()) {
	      String[] values = (String[])params.get(key);
	      for (int i = 0; i < values.length; ++i) {
	        String value = values[i];
	        paramlist = paramlist + key + "=" + value + "&";
	      }
	    }
	    return paramlist;
	  }

	
	
	
	
	
	
	
	
	
	
	
	
	       private static String writeLogInfo(String[] paramNames, JoinPoint joinPoint){  
	           Object[] args = joinPoint.getArgs();  
	           StringBuilder sb = new StringBuilder();  
	           boolean clazzFlag = true;  
	           for(int k=0; k<args.length; k++){  
	               Object arg = args[k];  
	               sb.append(paramNames[k]+" ");  
	               // 获取对象类型  
	               String typeName = arg.getClass().getTypeName();  
	                 
	               for (String t : types) {  
	                   if (t.equals(typeName)) {  
	                       sb.append("=" + arg+"; ");  
	                   }  
	               }  
	               if (clazzFlag) {  
	                   sb.append(getFieldsValue(arg));  
	               }  
	           }  
	           return sb.toString();  
	       }  
	       /** 
	        * 得到方法参数的名称 
	        * @param cls 
	        * @param clazzName 
	        * @param methodName 
	        * @return 
	        * @throws NotFoundException 
	        */  
	       private static String[] getFieldsName(Class cls, String clazzName, String methodName) throws NotFoundException{  
	           ClassPool pool = ClassPool.getDefault();  
	           //ClassClassPath classPath = new ClassClassPath(this.getClass());  
	           ClassClassPath classPath = new ClassClassPath(cls);  
	           pool.insertClassPath(classPath);  
	             
	           CtClass cc = pool.get(clazzName);  
	           CtMethod cm = cc.getDeclaredMethod(methodName);  
	           MethodInfo methodInfo = cm.getMethodInfo();  
	           CodeAttribute codeAttribute = methodInfo.getCodeAttribute();  
	           LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);  
	           if (attr == null) {  
	               // exception  
	           }  
	           String[] paramNames = new String[cm.getParameterTypes().length];  
	           int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;  
	           for (int i = 0; i < paramNames.length; i++){  
	               paramNames[i] = attr.variableName(i + pos); //paramNames即参数名  
	           }  
	           return paramNames;  
	       }  
	       /** 
	        * 得到参数的值 
	        * @param obj 
	        */  
	       public static String getFieldsValue(Object obj) {  
	           Field[] fields = obj.getClass().getDeclaredFields();  
	           String typeName = obj.getClass().getTypeName();  
	           for (String t : types) {  
	               if(t.equals(typeName))  
	                   return "";  
	           }  
	           StringBuilder sb = new StringBuilder();  
	           sb.append("【");  
	           for (Field f : fields) {  
	               f.setAccessible(true);  
	               try {  
	                   for (String str : types) {  
	                       if (f.getType().getName().equals(str)){  
	                           sb.append(f.getName() + " = " + f.get(obj)+"; ");  
	                       }  
	                   }  
	               } catch (IllegalArgumentException e) {  
	                   e.printStackTrace();  
	               } catch (IllegalAccessException e) {  
	                   e.printStackTrace();  
	               }  
	           }  
	           sb.append("】");  
	           return sb.toString();  
	       }  
	         
	       /** 
	        * 得到用户的登陆信息--这个还未实现，只是在网上抄的一段 
	        * @param joinPoint 
	        * @throws Exception 
	        */  
	       public void adminOptionContent(JoinPoint joinPoint) throws Exception {  
	           StringBuffer rs = new StringBuffer();  
	           String className = null;  
	           int index = 1;  
	           Object[] args = joinPoint.getArgs();  
	     
	           for (Object info : args) {  
	               // 获取对象类型  
	               className = info.getClass().getName();  
	               className = className.substring(className.lastIndexOf(".") + 1);  
	               rs.append("[参数" + index + "，类型：" + className + "，值：");  
	               // 获取对象的所有方法  
	               Method[] methods = info.getClass().getDeclaredMethods();  
	               // 遍历方法，判断get方法  
	               for (Method method : methods) {  
	                   String methodName = method.getName();  
	                   System.out.println(methodName);  
	                   // 判断是不是get方法  
	                   if (methodName.indexOf("get") == -1) {// 不是get方法  
	                       continue;// 不处理  
	                   }  
	                   Object rsValue = null;  
	                   try {  
	                       // 调用get方法，获取返回值  
	                       rsValue = method.invoke(info);  
	                       if (rsValue == null) {// 没有返回值  
	                           continue;  
	                       }  
	                   } catch (Exception e) {  
	                       continue;  
	                   }  
	                   // 将值加入内容中  
	                   rs.append("(" + methodName + " : " + rsValue + ")");  
	               }  
	               rs.append("]");  
	               index++;  
	           }  
	           System.out.println(rs.toString());  
	       }  
	     
	       private void getParamterName(String clazzName, String methodName)  
	               throws NotFoundException {  
	           ClassPool pool = ClassPool.getDefault();  
	           ClassClassPath classPath = new ClassClassPath(this.getClass());  
	           pool.insertClassPath(classPath);  
	     
	           CtClass cc = pool.get(clazzName);  
	           CtMethod cm = cc.getDeclaredMethod(methodName);  
	           MethodInfo methodInfo = cm.getMethodInfo();  
	           CodeAttribute codeAttribute = methodInfo.getCodeAttribute();  
	           LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute  
	                   .getAttribute(LocalVariableAttribute.tag);  
	           if (attr == null) {  
	               // exception  
	           }  
	           String[] paramNames = new String[cm.getParameterTypes().length];  
	           int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;  
	           for (int i = 0; i < paramNames.length; i++)  
	               paramNames[i] = attr.variableName(i + pos);  
	           // paramNames即参数名  
	           for (int i = 0; i < paramNames.length; i++) {  
	               System.out.println(paramNames[i]);  
	           }  
	       }  
	
	
}
