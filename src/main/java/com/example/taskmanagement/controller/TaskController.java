package com.example.taskmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanagement.service.TaskService;
import com.example.taskmanagement.dto.TaskFilterRequest;
import com.example.taskmanagement.model.Task;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController // Indicate that this is a Spring-managed REST controller
public class TaskController {

    private final Task task;
    @Autowired // dependency injection of TaskService(service)
    TaskService taskService;

    TaskController(Task task) {
        this.task = task;
    }
    //TaskService.java have the following  methods
    //getTask(),getTaskById(),addTask(),updateTask,deleteTask
    @GetMapping("/task")
    public List<Task> getTasks() {
        return taskService.getTasks();
    }
    @GetMapping("/task/{taskId}")
    public Task getTaskById(@PathVariable int taskId){
        return taskService.getTaskById(taskId);
    }
    @PostMapping("/task")
    public void addTask(@RequestBody Task task){
        taskService.addTask(task);
        System.out.println(task + " added successfully");
    }
    @PutMapping("/task/{id}")
    public Task updateTask(@PathVariable int id, @RequestBody Task task){
        System.out.println("putMapping controller trieggered, got task/id & task body");
        return taskService.updateTask(id, task);
    }
    @DeleteMapping("/task/{taskId}")
    public void deleteTask(@PathVariable int taskId){
        taskService.deleteTask(taskId);
        System.out.println("Task with id " + taskId + " deleted successfully");
    }
    // @GetMapping("/task/createdby")
    // public List<Task> getTasksByCreatedBy(@RequestParam String createdby){
    //     List<Task> tasks = taskService.findByCustom(createdby);
    //     return tasks;
    // }
    @PostMapping("/task/sql")
    public List<Task> getTasksByCreatedByAndDate(@RequestBody TaskFilterRequest request) {
        String createdBy = request.getCreatedBy();
        LocalDate date = request.getDate();
        return taskService.findByCreatedByAndDate(createdBy, date);
    }
      
}
