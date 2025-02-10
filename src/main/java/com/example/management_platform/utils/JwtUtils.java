package com.example.management_platform.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;


public class JwtUtils {

    @Value("${jwt.secret}")
    private static String key;

    @Value("${jwt.expiration}")
    private static long EXPIRE_TIME;
    /**
     * 生成JWT令牌
     * @param claims JWT第二部分负载 payload 中存储的内容
     * @return
     */
    public static String generateJwt(Map<String, Object> claims){
        String jwt = Jwts.builder()
                .setClaims(claims)//自定义信息（有效载荷）
                .signWith(SignatureAlgorithm.HS256,key)//签名算法（头部）
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))//过期时间
                .compact();
        return jwt;
    }

    /**
     * 解析JWT令牌
     * @param jwt JWT令牌
     * @return JWT第二部分负载 payload 中存储的内容
     */
    public static Claims parseJWT(String jwt){
        Claims claims = Jwts.parser()
                .setSigningKey(key)//指定签名密钥
                .parseClaimsJws(jwt)//指定令牌Token
                .getBody();
        return claims;
    }
}

