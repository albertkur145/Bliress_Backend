package com.blibli.future.phase2.component;

import com.blibli.future.phase2.entity.enumerate.Role;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    @SuppressWarnings("unchecked")
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();

        String username;
        try {
            username = jwtTokenProvider.getUsernameFromToken(authToken);
        } catch (Exception e) {
            username = null;
        }
        if (username != null && jwtTokenProvider.validateToken(authToken)) {
            Claims claims = jwtTokenProvider.getAllClaimsFromToken(authToken);
            List<String> rolesMap = claims.get("role", List.class);
            List<Role> roles = new ArrayList<>();
            for (String rolemap : rolesMap) {
                roles.add(Role.valueOf(rolemap));
            }
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    roles.stream().map(authority -> new SimpleGrantedAuthority(authority.name())).collect(Collectors.toList())
            );
            return Mono.just(auth);
        } else {
            return Mono.empty();
        }
    }
}
