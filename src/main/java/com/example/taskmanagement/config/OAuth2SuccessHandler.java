package com.example.taskmanagement.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.taskmanagement.service.JWTService;
import com.example.taskmanagement.service.UserService;

import java.io.IOException;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JWTService jwtService;
    private final UserService userService;

    public OAuth2SuccessHandler(JWTService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String username = oAuth2User.getAttribute("login"); // GitHub username, Google name, or fallback
        String provider = authentication.getAuthorities().iterator().next().getAuthority(); // optional
        String providerId = oAuth2User.getName(); // unique OAuth2 ID


        // Use service to handle DB logic + generate JWT
        String token = userService.processOAuthPostLogin(email, username, provider, providerId);

        

        response.setContentType("application/json");
        response.getWriter().write("{\"token\": \"" + token + "\"}");
        response.getWriter().flush();
    }
}