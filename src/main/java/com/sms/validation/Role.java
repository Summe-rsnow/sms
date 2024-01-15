package com.sms.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {RoleValidation.class})// 标明由哪个类执行校验逻辑
public @interface Role {
    // 校验出错时默认返回的消息
    String message() default "角色参数不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 同一个元素上指定多个该注解时使用
     */
    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        Role[] value();
    }
}

class RoleValidation implements ConstraintValidator<Role, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        // null 不做检验
        if (value == null) {
            return true;
        }
        return value == 0 || value == 1 || value == 2;
    }
}
