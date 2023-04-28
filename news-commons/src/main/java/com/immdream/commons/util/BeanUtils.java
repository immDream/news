package com.immdream.commons.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * 手动获取 Bean对象
 * <p>
 * news com.immdream.commons.util
 *
 * @author immDream
 * @date 2023/03/13/17:21
 * @since 1.8
 */
@Configuration
public class BeanUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BeanUtils.applicationContext = applicationContext;
    }

    public static  <T> T getBean(Class<T> c){
        return applicationContext.getBean(c);
    }
}
