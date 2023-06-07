package com.immdream.usermanager.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 登录 AOp
 * <p>
 * news com.immdream.usermanager.aop
 *
 * @author immDream
 * @date 2023/05/24/20:23
 * @since 1.8
 */
@Aspect
@Component
public class LoginAspect {

    @Pointcut("execution(public * com.immdream.usermanager.controller.UserCenterController.login*(..))")
    public void loginPointcut() {}

    /**
     *
     */
    @Before("loginPointcut()")
    public void loginBefore() {

    }
}
