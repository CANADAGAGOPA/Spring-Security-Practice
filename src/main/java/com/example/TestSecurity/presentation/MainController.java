package com.example.TestSecurity.presentation;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Iterator;

@Controller
public class MainController {

    @GetMapping("/")
    public String mainP(Model model) {

        // 현재 로그인한 사용자의 아이디를 가져옴
        String id = SecurityContextHolder.getContext().getAuthentication().getName();

        // 현재 로그인한 사용자의 권한을 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 권한이 여러 개일 수 있으므로 Collection 타입으로 받음
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        model.addAttribute("id", id); // id를 모델에 추가
        model.addAttribute("role", role); // role을 모델에 추가

        return "main";
    }
}
