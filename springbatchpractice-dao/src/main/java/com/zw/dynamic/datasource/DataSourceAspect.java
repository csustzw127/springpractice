package com.zw.dynamic.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.zw.dynamic.datasource.annotation.DynamicDataSource;


@Aspect
@Component
public class DataSourceAspect {

	/**
     * 定义前置通知
     * execution(* biz.UserBiz.*(..)) 表示  所有修饰符的所有返回值类型  mapper.UserBiz 包下的所有方法
     * 在方法执行之前执行
     * */
	 @Before("execution(* com.zw.service.*.*(..))")
	 public void before(JoinPoint point) {
		 	System.out.println("enter before...");
	        Object target = point.getTarget();
	        String method = point.getSignature().getName();

	        Class<?>[] classz = target.getClass().getInterfaces();

	        Class parameterTypes =  point.getSignature().getDeclaringType();
	        
	        // 如果被切面注解的类也是被动态数据源注解的类,需要设置数据源
	        DynamicDataSource d = target.getClass().getAnnotation(DynamicDataSource.class);
//	                .getMethod(method, parameterTypes).getParameterTypes();
	        if(d != null && method.matches("^find[a-zA-Z1-9]*$")) {
	        	DynamicDataSourceHolder.setDataSource("slave");
	        } else {
	        	DynamicDataSourceHolder.setDataSource("master");
	        }
//	        try {
//	            Method m = classz[0].getMethod(method, parameterTypes);
//	            if (m != null && m.isAnnotationPresent(DynamicDataSource.class)) {
//	            	DynamicDataSourceImpl data = m
//	                        .getAnnotation(DynamicDataSourceImpl.class);
//	                DynamicDataSourceHolder.putDataSource(data.value());
//	                System.out.println(data.value());
//	            }
//	            
//	        } catch (Exception e) {
//	            // TODO: handle exception
//	        }
	    }
}
