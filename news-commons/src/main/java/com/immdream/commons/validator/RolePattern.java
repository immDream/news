package com.immdream.commons.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 角色匹配注解
 * <p>
 * news com.immdream.commons.validator
 *
 * @author immDream
 * @date 2023/04/13/17:25
 * @since 1.8
 */
@Target( {ElementType.PARAMETER, ElementType.FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = RolePatternValidator.class)
@Documented
public @interface RolePattern {
    String message() default "至少有一个属性不可为空";

    /**
     * 权限
     */
    String regexp() default "" ;
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
