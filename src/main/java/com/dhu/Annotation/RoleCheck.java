package com.dhu.Annotation;

import java.lang.annotation.*;


/**
 * 自定义注解，用于角色权限检查
 * 在方法上使用，指定需要访问的角色
 * 例如：@RoleCheck({"admin,company"}) 表示只有 admin和company 角色可以访问该方法
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RoleCheck {
    String[] value(); // 允许访问的角色
}
