package com.example.taskmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.taskmanagement.repo.UserRepo;
import com.example.taskmanagement.model.UserPrincipal;
import com.example.taskmanagement.model.Users;

@Service
public class MyUserDetailsService implements UserDetailsService{
    // we want to connet with user repo
    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // we will load a user using username, from database. so we will create a repoe interface
        // we manually created database telusko1, table users, fields and some entries in the table using postgresql

        Users user = repo.findByUsername(username);
        if(user == null){
            System.out.println("User Not Found  "+ username);
            throw new UsernameNotFoundException("User  " + username + " not found");
        }

        //its tiem to work with UserDetails, we will use our own class UserPrincipal which implements UserDetails interface
        return new UserPrincipal(user);
    }    
}
