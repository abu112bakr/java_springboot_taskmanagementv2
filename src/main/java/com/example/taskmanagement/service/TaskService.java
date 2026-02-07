package com.example.taskmanagement.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taskmanagement.repo.TaskRepo;
import com.example.taskmanagement.model.Task;
import com.example.taskmanagement.model.Status;
import com.example.taskmanagement.util.SecurityUtils;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.taskmanagement.model.UserPrincipal;
import com.example.taskmanagement.exception.TaskNotFoundException;
@Service// Indicate that this is a Spring-managed service component
public class TaskService {
    @Autowired
    TaskRepo taskRepo; // dependency injection of TaskRepo(repository)
    // Task.java have the following  methods
    // getTaskId, setTaskId,getTaskName,setTaskName,getTaskDescription,SetTaskDescription,GetTaskStatus,setTaskStatus,toString

    public List<Task> getTasks() {
        return taskRepo.findAll();
    }
    public Task getTaskById(int taskId){
        //return taskRepo.findById(taskId).orElse(new Task( 0, "Not Found", "Not Found", Status.PENDING));
        //using exception handling
        return taskRepo.findById(taskId).orElseThrow(() -> new com.example.taskmanagement.exception.TaskNotFoundException("Task with id " + taskId + " not found"));
  
    }
    public Task addTask(Task task){
        // 1. Get current authentication
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // 2. Get UserPrincipal from authentication(get logged-in user)
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        // 3. set createdBy field in task
        task.setCreatedBy(userPrincipal.getUsername());
        task.setCreatedById(userPrincipal.getId());
        //is this two line code needed?
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        // 3 Save task
        //taskRepo.save(task);        
        return taskRepo.save(task);
    }
    public Task updateTask(int id, Task task){
        // 1. Get existing task from database
        Task existingTask = taskRepo.findById(id).orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));
        // 2. Get looged in user
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        // 3. Check if logged in user is the creator of the task
        //System.out.println(existingTask.getCreatedById() + " != == " + userPrincipal.getId());
        
        UserPrincipal userPrincipal = SecurityUtils.getCurrentUser();
        System.out.println(existingTask.getCreatedById() + " != == " + userPrincipal.getId());


        
        if (existingTask.getCreatedById() != userPrincipal.getId()) {
            System.out.println("unauthorized person trying to update it");
            throw new com.example.taskmanagement.exception.UnauthorizedException("You are not authorized to update this task");
        }
        // 4. Update only allowed fields
        existingTask.setTaskName(task.getTaskName());
        existingTask.setTaskDescription(task.getTaskDescription());
        existingTask.setTaskStatus(task.getTaskStatus());
        //taskRepo.save(task);
        // createdBy, createdAt remain unchanged automatically
        
        //save task and return
        System.out.println("Task " + existingTask.getTaskId() + " updated by " + userPrincipal.getUsername());
        //taskRepo.save(existingTask);
        return taskRepo.save(existingTask);
    }
    public void deleteTask(int taskId){
        taskRepo.deleteById(taskId);
        System.out.println("Task with id " + taskId + " deleted successfully");
    }
    public LocalDate getCurrentDate(){
        return LocalDate.now();
    }
    public LocalDateTime getUpDateAt(){
        return LocalDateTime.now();
    }    
}
