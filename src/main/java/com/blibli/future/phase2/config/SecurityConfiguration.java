package com.blibli.future.phase2.config;

import com.blibli.future.phase2.component.AuthenticationManager;
import com.blibli.future.phase2.component.SecurityContextRepository;
import com.blibli.future.phase2.controller.ApiPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
public class SecurityConfiguration {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SecurityContextRepository securityContextRepository;

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**"
    };

    @Bean
    public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {
        return http
                .cors()
                .and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((swe, e) -> {
                    return Mono.fromRunnable(() -> {
                        swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    });
                })
                .accessDeniedHandler((swe, e) -> {
                    return Mono.fromRunnable(() -> {
                        swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                    });
                })
                .and()
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .headers().frameOptions().disable()
                .and()
                .authorizeExchange()
                .pathMatchers(AUTH_WHITELIST).permitAll()
                .pathMatchers(ApiPath.AUTH_CHANGE_PASSWORD).authenticated()
                .pathMatchers(ApiPath.BASE_ADMIN + "/**").hasRole("ADMIN")
                .pathMatchers(ApiPath.BASE_TRAINER + "/**").hasRole("TRAINER")
                .pathMatchers(ApiPath.BASE_EMPLOYEE + "/**").hasRole("EMPLOYEE")
                .pathMatchers(ApiPath.AUTH + "/**").permitAll()
                .anyExchange().denyAll()
                .and().build();
    }


}
