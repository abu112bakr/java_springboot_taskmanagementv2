package com.example.taskmanagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.taskmanagement.model.Task;
@Repository //Task is the classname(tablename), Integer is the datatype of primary key
public interface TaskRepo extends JpaRepository<Task, Integer> {

}
//public interface TaskRepo extends JpaRepository<Task, Integer>
//gives CRUD methods for Task entity with primary key of type Integer
//gives save(), findById(), findAll(), deleteById(), etc.
// Controller
//    ↓
// Service
//    ↓
// Repository (JPA)
//    ↓
// Hibernate
//    ↓
// Database