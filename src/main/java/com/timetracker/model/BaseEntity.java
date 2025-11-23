package com.timetracker.model;

/**
 * Base entity class demonstrating inheritance
 * All model entities can extend this class
 */
public abstract class BaseEntity {
    protected int id;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Abstract method for validation
     * Demonstrates polymorphism - each entity validates differently
     */
    public abstract boolean isValid();
}

