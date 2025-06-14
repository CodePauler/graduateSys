package com.dhu.Utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtils {

    static final String SECRET_KEY="bmloYW8=";
    static final long EXPIRE_TIME_MILLIS=12*60*60*1000;
    /**
     * 生成JWT Token
     * @param claims 负载信息
     * @return JWT字符串
     */
    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME_MILLIS))
                .compact();
    }

    /**
     * 解析JWT Token
     * @param token JWT字符串
     * @return 解析后的负载信息
     */
    public static Claims parseToken(String token) throws Exception {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    // 私有构造方法防止实例化
    private JwtUtils() {}
}