package com.timetracker.util;

import com.timetracker.model.Task;
import com.timetracker.dao.TimeLogDAO;
import com.timetracker.dao.TaskDAO;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

/**
 * ReportGenerator class demonstrating:
 * - Collections & Generics (Map, Set, List with generics)
 * - Multithreading & Synchronization (ConcurrentHashMap, ReentrantReadWriteLock)
 * - Thread-safe operations
 */
public class ReportGenerator {
    
    private final TimeLogDAO timeLogDAO;
    private final TaskDAO taskDAO;
    
    // Using ConcurrentHashMap for thread-safe map operations
    private final Map<Integer, Double> projectHoursCache = new ConcurrentHashMap<>();
    
    // Using ReentrantReadWriteLock for synchronization
    private final ReentrantReadWriteLock cacheLock = new ReentrantReadWriteLock();
    
    // Using Set for unique collections
    private final Set<Integer> processedProjects = Collections.synchronizedSet(new HashSet<>());
    
    public ReportGenerator() {
        this.timeLogDAO = new TimeLogDAO();
        this.taskDAO = new TaskDAO();
    }
    
    /**
     * Generates a report of hours spent per project
     * Demonstrates thread-safe operations with synchronization
     */
    public Map<Integer, Double> generateProjectHoursReport() {
        cacheLock.readLock().lock();
        try {
            // Check cache first
            if (!projectHoursCache.isEmpty()) {
                return new HashMap<>(projectHoursCache);
            }
        } finally {
            cacheLock.readLock().unlock();
        }
        
        // Write lock for cache update
        cacheLock.writeLock().lock();
        try {
            // Double-check after acquiring write lock
            if (!projectHoursCache.isEmpty()) {
                return new HashMap<>(projectHoursCache);
            }
            
            // Generate report using collections and generics
            Map<Integer, Double> report = new HashMap<>();
            List<Task> tasks = taskDAO.getAllTasks();
            
            // Using Stream API with generics
            Map<Integer, List<Task>> tasksByProject = tasks.stream()
                .collect(Collectors.groupingBy(Task::getProjectId));
            
            for (Map.Entry<Integer, List<Task>> entry : tasksByProject.entrySet()) {
                int projectId = entry.getKey();
                List<Task> projectTasks = entry.getValue();
                
                double totalHours = 0.0;
                for (Task task : projectTasks) {
                    totalHours += timeLogDAO.getTotalHoursByTaskId(task.getId());
                }
                
                report.put(projectId, totalHours);
                processedProjects.add(projectId);
            }
            
            // Update cache
            projectHoursCache.putAll(report);
            return new HashMap<>(report);
            
        } finally {
            cacheLock.writeLock().unlock();
        }
    }
    
    /**
     * Clears the cache (thread-safe)
     */
    public void clearCache() {
        cacheLock.writeLock().lock();
        try {
            projectHoursCache.clear();
            processedProjects.clear();
        } finally {
            cacheLock.writeLock().unlock();
        }
    }
    
    /**
     * Gets processed projects (using Set for uniqueness)
     */
    public Set<Integer> getProcessedProjects() {
        return new HashSet<>(processedProjects);
    }
}

