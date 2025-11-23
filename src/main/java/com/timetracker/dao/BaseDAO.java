package com.timetracker.dao;

import com.timetracker.exception.DatabaseException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Base DAO class demonstrating inheritance
 * Provides common database operations for all DAOs
 */
public abstract class BaseDAO {
    
    /**
     * Gets a database connection
     * @return Connection object
     * @throws DatabaseException if connection fails
     */
    protected Connection getConnection() throws DatabaseException {
        try {
            return com.timetracker.util.DatabaseManager.getInstance().getConnection();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get database connection", e);
        }
    }
    
    /**
     * Closes a connection safely
     * @param conn Connection to close
     */
    protected void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

