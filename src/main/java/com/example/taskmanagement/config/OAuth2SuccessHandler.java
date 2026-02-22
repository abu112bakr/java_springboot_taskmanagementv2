package com.example.taskmanagement.config;


import com.example.taskmanagement.repo.UserRepo;
import com.example.taskmanagement.model.Users;
import com.example.taskmanagement.service.JWTService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserRepo userRepository;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) 
            throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        

        String providerId = oAuth2User.getAttribute("id").toString();

        // Check if user exists in DB, otherwise create
        Users user = userRepository.findByEmail(email)
        .orElseGet(() -> {
            Users newUser = new Users();
            newUser.setEmail(email);
            newUser.setUsername(email);
            newUser.setProvider("github");
            newUser.setProviderId(providerId);
            return userRepository.save(newUser);
        });

        // Generate JWT token for frontend
        String token = jwtService.generateToken(user.getUsername());

        // Return JWT as JSON
        response.setContentType("application/json");
        response.getWriter().write("{\"token\": \"" + token + "\"}");
    }
    
}
