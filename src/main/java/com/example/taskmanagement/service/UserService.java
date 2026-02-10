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

@Service
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
}
