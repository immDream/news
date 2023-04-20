package com.immdream.commons.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created with IntelliJ IDEA.
 * <p>
 * news com.immdream.commons.validator
 *
 * @author immDream
 * @date 2023/04/13/17:25
 * @since 1.8
 */
public class RolePatternValidator implements ConstraintValidator<RolePattern, Number> {
    private String regexp;

    @Override
    public void initialize(RolePattern constraintAnnotation) {
        this.regexp = constraintAnnotation.regexp();
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        if (value!=null){
            return value.toString().matches(regexp);
        }
        return true;
    }

}
