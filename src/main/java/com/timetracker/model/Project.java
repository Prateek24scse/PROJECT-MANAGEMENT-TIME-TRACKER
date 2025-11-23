package com.timetracker.model;

import java.time.LocalDate;

/**
 * Project model class demonstrating inheritance from BaseEntity
 */
public class Project extends BaseEntity {
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProjectStatus status;

    public Project() {}

    public Project(String title, String description, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = ProjectStatus.ACTIVE;
    }

    // Getters and Setters
    // getId() and setId() inherited from BaseEntity

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public ProjectStatus getStatus() { return status; }
    public void setStatus(ProjectStatus status) { this.status = status; }

    /**
     * Validates project data - demonstrates polymorphism
     */
    @Override
    public boolean isValid() {
        return title != null && !title.trim().isEmpty() &&
               startDate != null &&
               endDate != null &&
               !endDate.isBefore(startDate);
    }
    
    public enum ProjectStatus {
        ACTIVE, COMPLETED, ON_HOLD, CANCELLED
    }
}