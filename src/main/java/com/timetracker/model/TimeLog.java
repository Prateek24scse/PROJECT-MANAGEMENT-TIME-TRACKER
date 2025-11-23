package com.timetracker.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * TimeLog model class demonstrating inheritance from BaseEntity
 */
public class TimeLog extends BaseEntity {
    private int taskId;
    private int userId;
    private LocalDate date;
    private double hoursSpent;
    private String description;
    private LocalDateTime createdAt;

    public TimeLog() {}

    public TimeLog(int taskId, int userId, LocalDate date, double hoursSpent, String description) {
        this.taskId = taskId;
        this.userId = userId;
        this.date = date;
        this.hoursSpent = hoursSpent;
        this.description = description;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    // getId() and setId() inherited from BaseEntity

    public int getTaskId() { return taskId; }
    public void setTaskId(int taskId) { this.taskId = taskId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public double getHoursSpent() { return hoursSpent; }
    public void setHoursSpent(double hoursSpent) { this.hoursSpent = hoursSpent; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    /**
     * Validates time log data - demonstrates polymorphism
     */
    @Override
    public boolean isValid() {
        return taskId > 0 &&
               userId > 0 &&
               date != null &&
               hoursSpent > 0 &&
               hoursSpent <= 24; // Reasonable limit
    }
}