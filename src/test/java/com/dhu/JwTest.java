package com.dhu;

import com.dhu.Mapper.StudentMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwTest {

    @Test
    public void testGenJwt(){
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",10);
        claims.put("username","zhangsan");

        long time = 1000*60*60*12;
        String jwt = Jwts.builder().signWith(SignatureAlgorithm.HS256,"wsZm8D45l")
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*12)) // 设置过期时间为12小时
                .compact();
        System.out.println(jwt);
    }

    @Test
    public void testParseJwt(){
        Claims claims = Jwts.parser()
                .setSigningKey("wsZm8D45l")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MTAsInVzZXJuYW1lIjoiemhhbmdzYW4iLCJleHAiOjE3NDk5NjY3MDd9.MLf_pOdrvR-rbb_JBF_iJD_c5PXo8G-geHeruo7RZ4c")
                .getBody();
        System.out.println(claims);
    }


}
