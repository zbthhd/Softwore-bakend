package com.example.management_platform.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JwtUtils {

    private final String key;
    private final long expireTime;

    public JwtUtils(@Value("${jwt.secret}") String key, @Value("${jwt.expiration}") long expireTime) {
        this.key = key;
        this.expireTime = expireTime;
    }

    /**
     * 生成JWT令牌
     * @param claims JWT第二部分负载 payload 中存储的内容
     * @return
     */
    public String generateJwt(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims) // 自定义信息（有效载荷）
                .signWith(SignatureAlgorithm.HS256, key) // 签名算法（头部）
                .setExpiration(new Date(System.currentTimeMillis() + expireTime)) // 过期时间
                .compact();
    }

    /**
     * 解析JWT令牌
     * @param jwt JWT令牌
     * @return JWT第二部分负载 payload 中存储的内容
     */
    public Claims parseJWT(String jwt) {
        return Jwts.parser()
                .setSigningKey(key) // 指定签名密钥
                .parseClaimsJws(jwt) // 指定令牌Token
                .getBody();
    }
}