// package com.immdream.publishnews.aop;
//
// import org.aspectj.lang.JoinPoint;
// import org.aspectj.lang.annotation.Aspect;
// import org.aspectj.lang.annotation.Before;
// import org.aspectj.lang.annotation.Pointcut;
// import org.aspectj.lang.reflect.MethodSignature;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.stereotype.Component;
//
// /**
//  * Created with IntelliJ IDEA.
//  * <p>
//  * news com.immdream.publishnews.aop
//  *
//  * @author immDream
//  * @date 2023/06/08/16:10
//  * @since 1.8
//  */
// @Component
// @Aspect
// public class MyCachedAOP {
//     private static final Logger Logger = LoggerFactory.getLogger(MyCachedAOP.class);
//
//     @Pointcut("@annotation(com.immdream.publishnews.aop.ClearCached)")
//     private void MyCache() {
//     }
//
//     @Before("MyCache()")
//     public void before(JoinPoint joinPoint) {
//         MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//         ClearCached myCache = signature.getMethod().getAnnotation(ClearCached.class);
//         System.out.println("[" + signature.getName() + " : start.....]");
//     }
// }
