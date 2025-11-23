package com.timetracker.model;

/**
 * User model class demonstrating inheritance from BaseEntity
 */
public class User extends BaseEntity {
    private String username;
    private String password;
    private UserRole role;
    private String email;
    private String fullName;

    public User() {}

    public User(String username, String password, UserRole role, String email, String fullName) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.fullName = fullName;
    }

    // Getters and Setters
    // getId() and setId() inherited from BaseEntity

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    /**
     * Validates user data - demonstrates polymorphism
     */
    @Override
    public boolean isValid() {
        return username != null && !username.trim().isEmpty() &&
               password != null && !password.trim().isEmpty() &&
               role != null &&
               email != null && !email.trim().isEmpty();
    }
}