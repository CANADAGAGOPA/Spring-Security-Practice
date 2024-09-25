package com.example.TestSecurity.presentation;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

//    @GetMapping("/login")
//    public String loginP(Model model, HttpServletRequest request) {
//        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
//        model.addAttribute("_csrf", csrfToken);
//        return "login";
//    }

    @GetMapping("/login")
    public String loginP() {

        return "login";
    }
}