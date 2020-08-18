package com.blibli.future.phase2.configuration;

import com.blibli.future.phase2.component.JwtTokenProvider;
import com.blibli.future.phase2.entity.User;
import com.blibli.future.phase2.entity.enumerate.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;

public class UserTokenProvider {

    private static final long serialVersionUID = 1L;

    private static String secret = "3056a30bbea2c728ae2280a44bede5cf75ff7cdaf8efd7898c394dc595442c84ef8beeaf0ecd0d0a372395f68c31097e2d1f4bb6c86b3d476a2c9918ae5dfe10";

    private static String expirationTime = "86400000";

    private static User userRole = User.builder()
            .usermail("user@mail.com")
            .username("user")
            .password("password")
            .roles(new HashSet<>(Collections.singleton(Role.ROLE_EMPLOYEE)))
            .build();

    private static User trainerRole = User.builder()
            .usermail("trainer@mail.com")
            .username("trainer")
            .password("password")
            .roles(new HashSet<>(Collections.singleton(Role.ROLE_TRAINER)))
            .build();

    private static User adminRole = User.builder()
            .usermail("admin@mail.com")
            .username("admin")
            .password("password")
            .roles(new HashSet<>(Collections.singleton(Role.ROLE_ADMIN)))
            .build();

    public static String getTokenFromRole(String role){
        User selectedUser;

        switch (role) {
            case "ROLE_EMPLOYEE" :
                selectedUser = userRole;
                break;
            case "ROLE_TRAINER" :
                selectedUser = trainerRole;
                break;
            case "ROLE_ADMIN" :
                selectedUser = adminRole;
                break;
            default :
                return null;
        }

        return generateToken(selectedUser);
    }

    private static String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRoles());
        return doGenerateToken(claims, user.getUsername());
    }

    private static String doGenerateToken(Map<String, Object> claims, String username) {
        Long expirationTimeLong = Long.parseLong(expirationTime); //in second

        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encodeToString(secret.getBytes()))
                .compact();
    }
}
