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
        String url = request.getRequestURL().toString();
        log.info("当前请求路径：{}", url);
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
            request.setAttribute("role", role);
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
