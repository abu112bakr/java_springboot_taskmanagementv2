package com.example.taskmanagement.dto;

import java.time.LocalDate;

public class TaskFilterRequest {
    private String createdBy;
    private LocalDate date;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }



    
}
