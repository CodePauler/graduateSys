package com.dhu.Utils;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {   //Jwt鉴权
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String jwt = request.getHeader("token");
        String uri = request.getRequestURI().toString();
        log.info("当前请求路径：{}", uri);
        // 只放行 GET /majors
        if ("/majors".equals(uri) && "GET".equalsIgnoreCase(request.getMethod())) {
            log.info("放行 GET /majors 请求");
            return true;
        }
        if(!StringUtils.hasLength(jwt)) {
            // 如果没有 token，直接返回未登录状态
            log.info("未携带 token，拒绝访问");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 未授权
            return false;
        }
//      解析token
        try{
            Claims claims = JwtUtils.parseToken(jwt); // 解析 JWT 得到 claims
            String role = (String) claims.get("role"); // 获取用户角色
            Integer userId = (Integer) claims.get("id"); // 获取用户ID
            request.setAttribute("role", role);
            request.setAttribute("userId", userId); // 将用户ID存入请求属性中
            log.info("解析 token 成功，用户角色: {}", role);
        }
        catch (Exception e){
            e.printStackTrace();
            log.info("token解析失败，拒绝访问");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 未授权
            return false;
        }

//      验证通过，放行
        log.info("token验证通过");
        return true;
    }
}
