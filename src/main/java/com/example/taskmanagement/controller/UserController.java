package com.example.taskmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.taskmanagement.model.Users;
import com.example.taskmanagement.service.UserService;


@RestController
public class UserController {

    @Autowired
    private UserService userService;  
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @GetMapping("/user")
    public java.util.List<Users> getUsers() {
        return userService.getUsers();
    }
    @PostMapping("/user")
    public Users addUser(@RequestBody Users user) {
        // Registration logic here
        return userService.register(user);
        //to store this registered user we need to work in service layer
    }
    @PostMapping("/register") 
    public Users register(@RequestBody Users user) {

        //setPassword expect a string password
        //in the user object we are already getting the password in model User class
        user.setPassword(encoder.encode(user.getPassword()));
        return userService.register(user); 
        //to store this registered user we need to work in service layer
    }

    @PostMapping("/loogin")
    public String login(@RequestBody Users user) {
        System.out.println(user);
        //return "Success fully logged in";
        return userService.verify(user);

        //return userService.login(user);
        
    }
    @PutMapping("/user/{id}")
    public Users UpdateUser(@PathVariable int id, @RequestBody Users user) {
        // Registration logic here
        user.setPassword(encoder.encode(user.getPassword()));
        System.out.println("PutMapping UpdateUser{id}, user");
        //return userService.updateUser(id, user);
        return userService.updateUser(id, user);
        //to store this registered user we need to work in service layer
    }

 
}
