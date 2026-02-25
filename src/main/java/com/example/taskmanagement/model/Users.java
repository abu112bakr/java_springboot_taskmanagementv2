package com.example.taskmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Users {
    // @Id
    // private int id;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; 
    private String username;
    private String password;
    //002-users-table-github-login.yaml
    private String email;
    private String provider;
    private String providerId;


    // REQUIRED BY JPA
    public Users() {
    }    
    // Constructor for easier object creation
    public Users (int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }       
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    // @Override 
    // public String toString() {
    //     return "Users [id=" + id + ", username=" + username + ", password=" + password + "]";
    // }
    public String getEmail() {
        return email;
    }    
    public void setEmail(String email) {
        this.email = email;
    }
    public String getProvider() {
        return provider;
    }    
    public void setProvider(String string) {
        this.provider = string;
    }
    public String getProviderId() {
        return providerId;
    }
    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }  
    
    @Override 
    public String toString() {
        return "Users [id=" + id + ", username=" + username + ", password=" + password 
                + ", email=" + email + ", provider=" + provider + ", providerId=" + providerId + "]";
    }

}
