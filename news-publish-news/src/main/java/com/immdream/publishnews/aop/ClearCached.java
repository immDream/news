package com.immdream.publishnews.aop;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * news com.immdream.publishnews.aop
 *
 * @author immDream
 * @date 2023/06/08/16:06
 * @since 1.8
 */
@Target({ElementType.TYPE, ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ClearCached {
    String[] names();
}
