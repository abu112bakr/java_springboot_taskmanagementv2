//CRUD operations for User entity
package com.example.taskmanagement.service;

import com.example.taskmanagement.model.Users;
import com.example.taskmanagement.repo.UserRepo;
//import com.example.taskmanagement.exception.UserNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Authentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;



@Service
public class UserService {
    @Autowired
    private UserRepo repo; // inject repository

    @Autowired
    private JWTService jwtService; // for JWT token generation

    @Autowired
    AuthenticationManager authManager; // for login verification

    // 1️⃣ Get all users
    public List<Users> getUsers() {
        return repo.findAll();
    }

    // // 2️⃣ Get user by ID
    // public Users getUserById(int id) {
    //     return repo.findById(id);
    //             //.orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    // }
    public Users register(Users user){

        //Actually saving the user in database is the responsibility of service layer, so we call this method from controller
        return repo.save(user);
         
    }

    // // 3️⃣ Add new user
    // public Users addUser(Users user) {
    //     return repo.save(user);
    // }

    // 4️⃣ Update existing user
    public Users updateUser(int id, Users user) {
        //simple logic, anyone can update user
        // 1. Get existing task from database
        System.out.println(user + " updated successfully");
        return repo.save(user);
    }

    // 5️⃣ Delete user
    public void deleteUser(int id) {
        repo.deleteById(id);
        System.out.println("User with id " + id + " deleted successfully");
    }

    // 6️⃣ Optional utility methods
    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    public LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
    // --------------------------
    // OAuth2 login: find or create user
    // --------------------------
    public String processOAuthPostLogin(String email, String username, String provider, String providerId) {
        
        //Optional<Users> existingUser = repo.findByEmail(email);
        Optional<Users> existingUser = Optional.empty();
        if (email != null) {
            existingUser = repo.findByEmail(email);
        }
    
        if (existingUser.isEmpty() && username != null) {
            existingUser = repo.findOptionalByUsername(username);
        }

        Users user;
        if (existingUser.isPresent()) {
            user = existingUser.get();
        } else {
            user = new Users();
            user.setEmail(email);
            user.setUsername(username != null ? username : email); // fallback to email if username missing
            user.setProvider(provider);
            user.setProviderId(providerId);
            user = repo.save(user); // save new user with auto-generated ID
        }

        // Generate JWT token
        //return jwtService.generateToken(user.getEmail());
        return jwtService.generateToken(user.getUsername()); // use username as JWT subject
    }
    // verify username & password based user
    public String verify(Users user) {
        System.out.println("Trying to authenticate: " + user.getUsername());

        Authentication authentication = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        
        System.out.println("Authenticated: " + authentication.isAuthenticated());    
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        }
        return "Not logged in";
        
            
    }
}  

