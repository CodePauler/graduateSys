package com.dhu.Annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RoleCheck {
    String[] value(); // 允许访问的角色
}
