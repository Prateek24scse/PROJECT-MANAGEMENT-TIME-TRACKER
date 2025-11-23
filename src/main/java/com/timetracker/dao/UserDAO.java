package com.timetracker.dao;

import com.timetracker.exception.DatabaseException;
import com.timetracker.model.User;
import com.timetracker.model.UserRole;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * UserDAO class demonstrating:
 * - Inheritance (extends BaseDAO)
 * - Interface implementation (implements DAO<User, Integer>)
 * - Exception handling (throws DatabaseException)
 * - JDBC operations
 */
public class UserDAO extends BaseDAO implements DAO<User, Integer> {
    
    // Interface implementation methods
    @Override
    public List<User> findAll() throws DatabaseException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users ORDER BY full_name";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to retrieve all users", e);
        }
        return users;
    }
    
    @Override
    public Optional<User> findById(Integer id) throws DatabaseException {
        String query = "SELECT * FROM users WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to find user by ID: " + id, e);
        }
        return Optional.empty();
    }
    
    @Override
    public boolean save(User entity) throws DatabaseException {
        if (entity.getId() > 0) {
            return updateUser(entity);
        } else {
            return createUser(entity);
        }
    }
    
    @Override
    public boolean deleteById(Integer id) throws DatabaseException {
        String query = "DELETE FROM users WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to delete user with ID: " + id, e);
        }
    }
    
    // Additional methods
    public User authenticate(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        } catch (SQLException | DatabaseException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<User> getAllUsers() {
        try {
            return findAll();
        } catch (DatabaseException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    public boolean updateUserRole(int userId, UserRole newRole) {
        String query = "UPDATE users SET role = ? WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, newRole.name());
            stmt.setInt(2, userId);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException | DatabaseException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean createUser(User user) {
        String query = "INSERT INTO users (username, password, role, email, full_name) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole().name());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getFullName());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException | DatabaseException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private boolean updateUser(User user) throws DatabaseException {
        String query = "UPDATE users SET username = ?, password = ?, role = ?, email = ?, full_name = ? WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole().name());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getFullName());
            stmt.setInt(6, user.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to update user", e);
        }
    }
    
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setRole(UserRole.valueOf(rs.getString("role")));
        user.setEmail(rs.getString("email"));
        user.setFullName(rs.getString("full_name"));
        return user;
    }
}