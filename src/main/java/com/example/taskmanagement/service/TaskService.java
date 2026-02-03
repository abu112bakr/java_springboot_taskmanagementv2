package com.example.taskmanagement.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taskmanagement.repo.TaskRepo;
import com.example.taskmanagement.model.Task;
import com.example.taskmanagement.model.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


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
        return taskRepo.save(task);
    }
    public void updateTask(Task task){
        taskRepo.save(task);
        System.out.println(task + " updated successfully");
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
