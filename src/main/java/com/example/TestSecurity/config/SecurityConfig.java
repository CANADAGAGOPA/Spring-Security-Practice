package com.example.TestSecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 해당 클래스가 스프링 시큐리티에 의해 관리되는 설정 클래스임을 명시
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    } // BCryptPasswordEncoder 빈 등록

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/login", "/loginProc", "/join", "/joinProc").permitAll() // 해당 요청은 인증 없이 접근 가능
                        .requestMatchers("/admin").hasRole("ADMIN") // 해당 요청은 ADMIN 권한이 있어야 접근 가능
                        .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER") // 해당 요청은 ADMIN, USER 권한이 있어야 접근 가능
                        .anyRequest().authenticated() // 그 외의 요청은 인증이 필요함
                );

        http
                .formLogin((auth) -> auth.loginPage("/login") // 로그인 페이지 경로
                        .loginProcessingUrl("/loginProc") // /loginProc 경로로 요청이 오면 로그인 처리
                        .permitAll() // 로그인 페이지는 인증 없이 접근 가능
                );

        http
                .csrf((auth) -> auth.disable() // 임시로 csrf 기능을 비활성화
                );

        // 다중 로그인 설정
        http
                .sessionManagement((auth) -> auth // 세션 관리
                        .maximumSessions(1) // 하나의 계정으로 한 번에 로그인 가능한 세션 수
                        .maxSessionsPreventsLogin(true) // 최대 세션 수 초과 시 로그인 방지
                        // maxSessionsPreventsLogin 는 다중 로그인 개수를 초과할 경우 로그인을 막을지 여부를 결정하는 메소드
                        // true 로 설정하면 다중 로그인을 막음, false 로 설정하면 기존 세션을 만료시킴
                );

        // 세션 고정 보호
        http
                .sessionManagement((auth) -> auth
                        .sessionFixation().changeSessionId() // 세션 고정 보호를 위해 세션 ID 를 변경
                );

        return http.build();
    }
}
