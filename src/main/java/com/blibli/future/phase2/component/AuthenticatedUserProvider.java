package com.blibli.future.phase2.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.LinkedHashMap;

@Component
public class AuthenticatedUserProvider {
    @Value("${app.jwtSecret}")
    private String secret;

    public Object getValueFromUserData(String token, String key){
        LinkedHashMap userData = getUserDataFromToken(token);
        return userData.get(key);
    }

    public LinkedHashMap getUserDataFromToken(String token){
        Claims claims = getAllClaimsFromToken(token);
        return claims.get("user", LinkedHashMap.class);
    }

    public String getTokenFromHeader(HttpHeaders headers) {
        return headers.getFirst(HttpHeaders.AUTHORIZATION).split(" ")[1];
    }

    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(secret.getBytes()))
                .parseClaimsJws(token).getBody();
    }
}
