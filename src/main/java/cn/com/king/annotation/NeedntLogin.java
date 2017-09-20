package cn.com.king.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
// 最高优先级
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface NeedntLogin {
	/**
	 * 
	 * 声明是否需要验证
	 */
	boolean validate() default true;
}
