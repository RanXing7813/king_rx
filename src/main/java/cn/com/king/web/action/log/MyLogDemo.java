//package cn.com.king.web.action.log;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.EnableAspectJAutoProxy;
//import org.springframework.stereotype.Component;
//
//import javassist.ClassClassPath;
//import javassist.ClassPool;
//import javassist.CtClass;
//import javassist.CtMethod;
//import javassist.Modifier;
//import javassist.NotFoundException;
//import javassist.bytecode.CodeAttribute;
//import javassist.bytecode.LocalVariableAttribute;
//import javassist.bytecode.MethodInfo;
//
////@Component("myLog")
////@EnableAspectJAutoProxy(proxyTargetClass=true)
////@Aspect
//public class MyLogDemo {
//	  private static String[] types = { "java.lang.Integer", "java.lang.Double",  
//	            "java.lang.Float", "java.lang.Long", "java.lang.Short",  
//	            "java.lang.Byte", "java.lang.Boolean", "java.lang.Char",  
//	            "java.lang.String", "int", "double", "long", "short", "byte",  
//	            "boolean", "char", "float" };  
//					
//	@Pointcut("execution(* cn.com.taiji.web.action.**.*(..)) && !execution(* cn.com.taiji.web.action.NotNeedLoginController.*(..))")//and !execution(* cn.com.taiji.web.action.NotNeedLoginController.*(..))
//	public void pointcut(){};
//	
//	
//    @Before(value = "pointcut()")
//    public void befores(JoinPoint joinpoint  ){  
//            joinpoint.getArgs();//此方法返回的是一个数组，数组中包括request以及ActionCofig等类对象  
//            System.out.println("被拦截方法调用之前调用此方法，输出此语句");  
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
//    }  
//		
//	@After(value = "pointcut()"  )
//	public void afters(JoinPoint joinpoint){  
//	              System.out.println("被拦截方法调用之后调用此方法，输出此语句");  
//	              //此方法返回的是一个数组，数组中包括request以及ActionCofig等类对象
////	    	        Object[] args = joinpoint.getArgs();
////	    	        System.out.println("目标参数列表：");
////	    	        if (args != null) {
////	    	            for (Object obj : args) {
////	    	                System.out.println(obj + ",");
////	    	            }
////	    	            System.out.println();
////	    	           / System.out.println("执行结果是：" + returnObj);
////	    	         }
//	}  
//	
//	@AfterReturning(value="pointcut()",returning="val")
//	public void endRecordLog(JoinPoint joinPoint,Object val)
//	       throws Throwable {  
//	           System.out.println("---------------");  
////	           String classType = joinPoint.getTarget().getClass().getName();  
////	           Class<?> clazz = Class.forName(classType);  
////	           String clazzName = clazz.getName();  
////	           String clazzSimpleName = clazz.getSimpleName();  
////	           String methodName = joinPoint.getSignature().getName();  
////	             
////	           String[] paramNames = getFieldsName(this.getClass(), clazzName, methodName);  
////	             
////	           String logContent = writeLogInfo(paramNames, joinPoint);  
////	             
////	           Logger logger = LoggerFactory.getLogger(clazzName);  
////	           logger.info("clazzName: "+clazzName+", methodName:"+methodName+", param:"+ logContent);  
//	             
//	       }  
//	       private static String writeLogInfo(String[] paramNames, JoinPoint joinPoint){  
//	           Object[] args = joinPoint.getArgs();  
//	           StringBuilder sb = new StringBuilder();  
//	           boolean clazzFlag = true;  
//	           for(int k=0; k<args.length; k++){  
//	               Object arg = args[k];  
//	               sb.append(paramNames[k]+" ");  
//	               // 获取对象类型  
//	               String typeName = arg.getClass().getTypeName();  
//	                 
//	               for (String t : types) {  
//	                   if (t.equals(typeName)) {  
//	                       sb.append("=" + arg+"; ");  
//	                   }  
//	               }  
//	               if (clazzFlag) {  
//	                   sb.append(getFieldsValue(arg));  
//	               }  
//	           }  
//	           return sb.toString();  
//	       }  
//	       /** 
//	        * 得到方法参数的名称 
//	        * @param cls 
//	        * @param clazzName 
//	        * @param methodName 
//	        * @return 
//	        * @throws NotFoundException 
//	        */  
//	       private static String[] getFieldsName(Class cls, String clazzName, String methodName) throws NotFoundException{  
//	           ClassPool pool = ClassPool.getDefault();  
//	           //ClassClassPath classPath = new ClassClassPath(this.getClass());  
//	           ClassClassPath classPath = new ClassClassPath(cls);  
//	           pool.insertClassPath(classPath);  
//	             
//	           CtClass cc = pool.get(clazzName);  
//	           CtMethod cm = cc.getDeclaredMethod(methodName);  
//	           MethodInfo methodInfo = cm.getMethodInfo();  
//	           CodeAttribute codeAttribute = methodInfo.getCodeAttribute();  
//	           LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);  
//	           if (attr == null) {  
//	               // exception  
//	           }  
//	           String[] paramNames = new String[cm.getParameterTypes().length];  
//	           int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;  
//	           for (int i = 0; i < paramNames.length; i++){  
//	               paramNames[i] = attr.variableName(i + pos); //paramNames即参数名  
//	           }  
//	           return paramNames;  
//	       }  
//	       /** 
//	        * 得到参数的值 
//	        * @param obj 
//	        */  
//	       public static String getFieldsValue(Object obj) {  
//	           Field[] fields = obj.getClass().getDeclaredFields();  
//	           String typeName = obj.getClass().getTypeName();  
//	           for (String t : types) {  
//	               if(t.equals(typeName))  
//	                   return "";  
//	           }  
//	           StringBuilder sb = new StringBuilder();  
//	           sb.append("【");  
//	           for (Field f : fields) {  
//	               f.setAccessible(true);  
//	               try {  
//	                   for (String str : types) {  
//	                       if (f.getType().getName().equals(str)){  
//	                           sb.append(f.getName() + " = " + f.get(obj)+"; ");  
//	                       }  
//	                   }  
//	               } catch (IllegalArgumentException e) {  
//	                   e.printStackTrace();  
//	               } catch (IllegalAccessException e) {  
//	                   e.printStackTrace();  
//	               }  
//	           }  
//	           sb.append("】");  
//	           return sb.toString();  
//	       }  
//	         
//	       /** 
//	        * 得到用户的登陆信息--这个还未实现，只是在网上抄的一段 
//	        * @param joinPoint 
//	        * @throws Exception 
//	        */  
//	       public void adminOptionContent(JoinPoint joinPoint) throws Exception {  
//	           StringBuffer rs = new StringBuffer();  
//	           String className = null;  
//	           int index = 1;  
//	           Object[] args = joinPoint.getArgs();  
//	     
//	           for (Object info : args) {  
//	               // 获取对象类型  
//	               className = info.getClass().getName();  
//	               className = className.substring(className.lastIndexOf(".") + 1);  
//	               rs.append("[参数" + index + "，类型：" + className + "，值：");  
//	               // 获取对象的所有方法  
//	               Method[] methods = info.getClass().getDeclaredMethods();  
//	               // 遍历方法，判断get方法  
//	               for (Method method : methods) {  
//	                   String methodName = method.getName();  
//	                   System.out.println(methodName);  
//	                   // 判断是不是get方法  
//	                   if (methodName.indexOf("get") == -1) {// 不是get方法  
//	                       continue;// 不处理  
//	                   }  
//	                   Object rsValue = null;  
//	                   try {  
//	                       // 调用get方法，获取返回值  
//	                       rsValue = method.invoke(info);  
//	                       if (rsValue == null) {// 没有返回值  
//	                           continue;  
//	                       }  
//	                   } catch (Exception e) {  
//	                       continue;  
//	                   }  
//	                   // 将值加入内容中  
//	                   rs.append("(" + methodName + " : " + rsValue + ")");  
//	               }  
//	               rs.append("]");  
//	               index++;  
//	           }  
//	           System.out.println(rs.toString());  
//	       }  
//	     
//	       private void getParamterName(String clazzName, String methodName)  
//	               throws NotFoundException {  
//	           ClassPool pool = ClassPool.getDefault();  
//	           ClassClassPath classPath = new ClassClassPath(this.getClass());  
//	           pool.insertClassPath(classPath);  
//	     
//	           CtClass cc = pool.get(clazzName);  
//	           CtMethod cm = cc.getDeclaredMethod(methodName);  
//	           MethodInfo methodInfo = cm.getMethodInfo();  
//	           CodeAttribute codeAttribute = methodInfo.getCodeAttribute();  
//	           LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute  
//	                   .getAttribute(LocalVariableAttribute.tag);  
//	           if (attr == null) {  
//	               // exception  
//	           }  
//	           String[] paramNames = new String[cm.getParameterTypes().length];  
//	           int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;  
//	           for (int i = 0; i < paramNames.length; i++)  
//	               paramNames[i] = attr.variableName(i + pos);  
//	           // paramNames即参数名  
//	           for (int i = 0; i < paramNames.length; i++) {  
//	               System.out.println(paramNames[i]);  
//	           }  
//	       }  
//	
//	
//}
