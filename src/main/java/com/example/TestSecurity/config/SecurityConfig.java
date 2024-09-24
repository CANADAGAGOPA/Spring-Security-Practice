package com.example.TestSecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 해당 클래스가 스프링 시큐리티에 의해 관리되는 설정 클래스임을 명시
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/login").permitAll() // 해당 요청은 인증 없이 접근 가능
                        .requestMatchers("/admin").hasRole("ADMIN") // 해당 요청은 ADMIN 권한이 있어야 접근 가능
                        .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER") // 해당 요청은 ADMIN, USER 권한이 있어야 접근 가능
                        .anyRequest().authenticated() // 그 외의 요청은 인증이 필요함
                );



        return http.build();
    }
}
