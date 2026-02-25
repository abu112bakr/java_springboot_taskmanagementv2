package com.example.taskmanagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.taskmanagement.model.Users;
import java.util.Optional; 

@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {
    //this findByUsername will be called in MyUserDetailsService.java
    Users findByUsername(String username);

    // For OAuth2 login, return Optional<Users>
    Optional<Users> findByEmail(String email); 
    Optional<Users> findOptionalByUsername(String username);
      

}
