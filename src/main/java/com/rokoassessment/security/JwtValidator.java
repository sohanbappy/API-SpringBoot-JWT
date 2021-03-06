package com.rokoassessment.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import com.rokoassessment.models.JwtUserDetails;

@Component
public class JwtValidator {


    private String secret = "rokomari";

    public JwtUserDetails validate(String token) {

        JwtUserDetails user = null;
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            user = new JwtUserDetails();
            user.setFirst_name(body.getSubject());
            user.setEmail((String) body.get("userEmail"));
            user.setPassword((String) body.get("userPassword"));
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return user;
    }
}
