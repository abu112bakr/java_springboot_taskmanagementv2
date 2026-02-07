package com.example.taskmanagement.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Component
@Entity
@EntityListeners(AuditingEntityListener.class) // `
public class Task {
    @Id //int taskId is the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)// Let DB generate IDs automatically, auto-incremented by the database 
    private int taskId;
    private String taskName;
    private String taskDescription;
    //private String taskStatus; // should be enum, PENDING, IN_PROGRESS, DONE
    @Enumerated(EnumType.STRING)
    private Status taskStatus;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    private String createdBy;
    private int createdById; // foreign key to Users table

    // REQUIRED BY JPA
    public Task(){

    }
    //is this constructor necessary?
    public Task(int taskId, String taskName, String taskDescription, Status taskStatus) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        //this.taskStatus = taskStatus;
        if (taskStatus == Status.PENDING) {
            this.taskStatus = Status.PENDING; 
        }
        else if (taskStatus == Status.IN_PROGRESS) {
            this.taskStatus = Status.IN_PROGRESS; 
        }
        else if (taskStatus == Status.DONE) {
            this.taskStatus = Status.DONE;
        }
        else{
            this.taskStatus = Status.PENDING; // default value
        }    

    }
    // getters and setters
    public int getTaskId() {
        return taskId;
    }
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
    public String getTaskName() {
        return taskName;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    public String getTaskDescription() {
        return taskDescription;
    }
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
    public Status getTaskStatus() {
        return taskStatus;
    }
    public void setTaskStatus(Status taskStatus) {
        this.taskStatus = taskStatus;
    }
    //All these needed to save and showup createdAd and updatedAt fields
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public int getCreatedById() {
        return createdById;
    }
    public void setCreatedById(int createdById) {
        this.createdById = createdById;
    }
    // toString method
    public String toString() {
        return "Task [taskId=" + taskId + ", taskName=" + taskName + ", taskDescription=" + taskDescription
                + ", taskStatus=" + taskStatus + "]";
    }    
}
