//CRUD operations for User entity
package com.example.taskmanagement.service;

import com.example.taskmanagement.model.Users;
import com.example.taskmanagement.repo.UserRepo;
//import com.example.taskmanagement.exception.UserNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
public class UserService {
    @Autowired
    private UserRepo repo; // inject repository

    // 1️⃣ Get all users
    public List<Users> getUsers() {
        return repo.findAll();
    }

    // // 2️⃣ Get user by ID
    // public Users getUserById(int id) {
    //     return repo.findById(id);
    //             //.orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    // }

    // 3️⃣ Add new user
    public Users addUser(Users user) {
        return repo.save(user);
    }

    // 4️⃣ Update existing user
    public void updateUser(Users user) {
        repo.save(user);
        System.out.println(user + " updated successfully");
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
}
