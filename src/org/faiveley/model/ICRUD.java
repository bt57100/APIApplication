/*
 * Faiveley Transport License
 */
package org.faiveley.model;

/**
 * Interface Create Read Update Delete
 * 
 * @author 813308
 */
public interface ICRUD {
    // Create a new object
    public void create();
    
    // Read an object
    public void read();
    
    // Update an object
    public void update();
    
    // Delete an object
    public void delete();
}
