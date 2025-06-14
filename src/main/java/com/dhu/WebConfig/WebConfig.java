package com.dhu.WebConfig;

import com.dhu.Utils.RoleInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.dhu.Utils.TokenInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Autowired
    private RoleInterceptor roleInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {     // 所有请求都需要经过token拦截器
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/register");

        registry.addInterceptor(roleInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/register");    //这里不排除也可以，因为/login和/register下的方法都不会加RoleCheck注解
    }
}
