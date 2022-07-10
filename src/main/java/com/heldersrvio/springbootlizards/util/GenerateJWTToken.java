package com.heldersrvio.springbootlizards.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class GenerateJWTToken {
    static final String SECRET = System.getenv("JWT_SECRET");
    static final long EXPIRATION_TIME = 7 * 24 * 60 * 60;
    public static String JWTGenerate(){
        return Jwts.builder()
                .setSubject("user")
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public static void main(String[] args) {
        String JWT = JWTGenerate();
        System.out.println(JWT);
    }
}
