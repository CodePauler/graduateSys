package com.dhu.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession(false);
        // 假设 session 中有 user 属性表示已登录
        if (session != null && session.getAttribute("user") != null) {
            return true;
        }
        // 前后端分离建议返回未登录状态码，由前端跳转
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("未登录");
        return false;
    }
}
