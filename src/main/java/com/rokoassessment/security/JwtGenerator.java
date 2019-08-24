package com.rokoassessment.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import com.rokoassessment.models.Doctor;
import com.rokoassessment.models.Patient;
import com.rokoassessment.models.User;

@Component
public class JwtGenerator {


    public String generate(User user) {

        Claims claims = Jwts.claims()
                .setSubject(user.getFirst_name());
        claims.put("userEmail", user.getEmail());
        claims.put("userPassword",user.getPassword());


        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, "rokomari")
                .compact();
    }
    public String generateDoctorToken(Doctor doc) {

        Claims claims = Jwts.claims()
                .setSubject(doc.getName());
        claims.put("docDept", String.valueOf(doc.getDept()));
        claims.put("docId", doc.getId());


        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, "rokomari")
                .compact();
    }
    public String generatePatientToken(Patient pat) {

        Claims claims = Jwts.claims()
                .setSubject(pat.getName());
        claims.put("patientMobile", String.valueOf(pat.getMobile()));
        claims.put("patientId", pat.getId());


        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, "rokomari")
                .compact();
    }
}
