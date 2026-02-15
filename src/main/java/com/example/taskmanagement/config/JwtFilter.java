package com.example.taskmanagement.config;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.taskmanagement.service.JWTService;
import com.example.taskmanagement.service.MyUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{
    // vs code suggested this bean
    // @Bean
    // public JwtFilter jwtFilter(){
    //     return new JwtFilter();
    // }
    @Autowired
    private JWTService jwtService;

    @Autowired
    ApplicationContext context;

    @Autowired
    private MyUserDetailsService userDetailsService;

    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain)
            throws ServletException, IOException {

        //throw new UnsupportedOperationException("Unimplemented method 'doFilterInternal'");
        // from client side we get
        // Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjb2QiLCJpYXQiOjE3NzExMzYzOTMsImV4cCI6MTc3MTEzNjUwMX0.zSPbJdNZalmBNiXtCcNpR40Co65ntWIIPcJoYJRBs28
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        //Chatgpt needed it to print out token from /loogin 
        if (request.getServletPath().equals("/loogin")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            username = jwtService.extractUserName(token);
        }
        //username exist & not already authenticated
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            //creating user details object
            //UserDetails userDetails = context.getBean((MyUserDetailsService.class).loadUserByUsername(username));
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            
            //we will validate then send it to user authentication service
            if(jwtService.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, 
                                null, 
                                userDetails.getAuthorities());
                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request));
                SecurityContextHolder.getContext()
                        .setAuthentication(authToken);


            }
            //continue filter
            filterChain.doFilter(request, response);

        }


    }

}
