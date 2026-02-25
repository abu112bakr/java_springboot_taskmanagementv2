package com.example.taskmanagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import com.example.taskmanagement.model.Task;
@Repository //Task is the classname(tablename), Integer is the datatype of primary key
public interface TaskRepo extends JpaRepository<Task, Integer> {
    // @Query("SELECT t FROM Task t WHERE t.createdBy = :createdby ")
    // List<Task> findByCreatedBy(String createdby);
    @Query("SELECT t FROM Task t " +
           "WHERE t.createdBy = :createdBy " +
           "AND FUNCTION('DATE', t.createdAt) = :date")
    List<Task> findByCreatedByAndDate(String createdBy, LocalDate date);


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