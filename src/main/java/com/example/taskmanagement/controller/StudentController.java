package com.example.taskmanagement.controller;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.ArrayList;
import com.example.taskmanagement.model.Student;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class StudentController {
    public List<Student> students = new ArrayList<>(List.of(
        new Student(1L, "Alice", 85),
        new Student(2L, "Bob", 90),
        new Student(3L, "Charlie", 78)
    ));
    
    @GetMapping("/student")
    public List<Student> getStudents() {
        return students;
    }
    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request){ 
        return (CsrfToken) request.getAttribute("_csrf");
        
    }
    @PostMapping("/student")
    public Student addStudent(@RequestBody Student student){
        students.add(student);
        return student;
    }    
}
