package com.rokoassessment.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import com.rokoassessment.models.User;

@Component
public class JwtGenerator {


    public String generate(User user) {

        Claims claims = Jwts.claims()
                .setSubject(user.getFirst_name());
        claims.put("userEmail", String.valueOf(user.getEmail()));
        claims.put("userId", user.getId());


        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, "rokomari")
                .compact();
    }
}
