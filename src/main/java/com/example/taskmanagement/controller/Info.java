package com.example.taskmanagement.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RestController;



import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import com.example.taskmanagement.model.UserPrincipal;

@RestController
public class Info {

    


    @GetMapping("/info")
    public String getInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        
        System.out.println("Username: " + userPrincipal.getUsername());
        System.out.println("User ID: " + userPrincipal.getId());
        return "Username: " + userPrincipal.getUsername() +
               ", User ID: " + userPrincipal.getId();
    }
            
    //System.out.println("Username: " + userPrincipal.getUsername());
    //System.out.println("User ID: " + userPrincipal.getId());

}
