package com.matchsystem.matchsystem;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class MatchSystemApplicationTests {
    private static final String SECRET_KEY = "lammy";

    // 过期时间，单位：毫秒，这里设置为一天
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000;

    @Test
    void contextLoads() {
        System.out.println("加载结束");
    }


    @Test
    public void createToken() {
       /* HashMap<String,Object> map = new HashMap<>();
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND,20);
        String token = JWT.create()
                .withHeader(map) //可以不设定，就是使用默认的
                .withClaim("userId",20)//payload  //自定义用户名
                .withClaim("username","zhangsan")
                .withExpiresAt(instance.getTime()) //指定令牌过期时间
                .sign(Algorithm.HMAC256("lammy"));//签名*/

       /* System.out.println(token);*/
        String token ="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJBY2NvdW50IjoiMTIzNHRlMyIsIkVtYWlsIjoiYWxjb3J0YWNlY2lseUBnbWFpbC5jb20iLCJJRCI6IjIyIiwiZXhwIjoxNzA5NTYzNzc4LCJQYXNzd29yZCI6IjEyMzQ1NiJ9.SFBNEUgvTR7sMhy83L7MaZygCHwCHjYUB0SuH0_RIqU";
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("lammy")).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        String a =decodedJWT.getClaim("ID").asString();
        String name = decodedJWT.getClaim("Account").asString();
        decodedJWT.getExpiresAt();//获取过期时间
        System.out.println(Integer.parseInt(a)+" "+name+" "+decodedJWT.getExpiresAt());
    }


}
