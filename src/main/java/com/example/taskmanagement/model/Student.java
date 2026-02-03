package com.example.taskmanagement.model;

public class Student {
    private Long id;
    private String name;
    private int marks;

    public Student(Long id, String name, int marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }    
    @Override // to override the default
    //toString method to print everything
    public String toString() {
        return "Student [id=" + id + ", name=" + name + ", marks=" + marks + "]";   
    }     
}
