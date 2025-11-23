package com.timetracker.dao;

import com.timetracker.exception.DatabaseException;
import java.util.List;
import java.util.Optional;

/**
 * Generic DAO interface demonstrating interfaces and generics
 * @param <T> The entity type
 * @param <ID> The ID type (usually Integer)
 */
public interface DAO<T, ID> {
    
    /**
     * Find all entities
     * @return List of all entities
     * @throws DatabaseException if database operation fails
     */
    List<T> findAll() throws DatabaseException;
    
    /**
     * Find entity by ID
     * @param id The entity ID
     * @return Optional containing the entity if found
     * @throws DatabaseException if database operation fails
     */
    Optional<T> findById(ID id) throws DatabaseException;
    
    /**
     * Save an entity (create or update)
     * @param entity The entity to save
     * @return true if successful
     * @throws DatabaseException if database operation fails
     */
    boolean save(T entity) throws DatabaseException;
    
    /**
     * Delete an entity by ID
     * @param id The entity ID
     * @return true if successful
     * @throws DatabaseException if database operation fails
     */
    boolean deleteById(ID id) throws DatabaseException;
}

