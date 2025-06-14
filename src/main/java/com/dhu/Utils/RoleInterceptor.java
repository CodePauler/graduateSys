package com.dhu.Utils;

import com.dhu.Annotation.RoleCheck;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

@Slf4j
@Component
public class RoleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        String url = request.getRequestURL().toString();
        log.info("当前请求路径：{}", url);
        if(!(handler instanceof HandlerMethod)) return true; // 请求不访问方法，直接放行

        HandlerMethod method = (HandlerMethod) handler;
        RoleCheck roleCheck = method.getMethodAnnotation(RoleCheck.class);
        if(roleCheck == null) return true; // 方法没有RoleCheck注解，直接放行

//        获取登录时保存的角色信息
        String role = (String) request.getAttribute("role");
        if(role == null || role.isEmpty()){
            log.info("未登录或角色信息缺失，拒绝访问");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 未登录
            return false;
        }

        // 检查角色是否匹配
        if (Arrays.asList(roleCheck.value()).contains(role)) {
            log.info("角色匹配，放行，当前角色: {}, 需要的角色: {}", role, Arrays.toString(roleCheck.value()));
            return true; // 角色匹配，放行
        } else {
            log.info("角色不匹配，拒绝访问，当前角色: {}, 需要的角色: {}", role, Arrays.toString(roleCheck.value()));
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 角色不匹配，无权限访问
            return false;
        }
    }
}
