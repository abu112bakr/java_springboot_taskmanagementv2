package com.example.taskmanagement.util;

import com.example.taskmanagement.service.TaskService;

import com.example.taskmanagement.model.UserPrincipal;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

public class SecurityUtils {

    public static UserPrincipal getCurrentUser() {
        // get logged in user
        // not using it right now
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (UserPrincipal) auth.getPrincipal();

    }
    
}
