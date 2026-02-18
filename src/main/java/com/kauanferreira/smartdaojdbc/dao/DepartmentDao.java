package com.kauanferreira.smartdaojdbc.dao;

import com.kauanferreira.smartdaojdbc.entity.Department;

import java.util.List;

/**
 * Data Access Object interface for {@link Department} entity.
 * Defines the standard CRUD operations to be implemented
 * by any persistence mechanism.
 *
 * @author Kauan
 * @version 1.0
 * @since 2026
 */
public interface DepartmentDao {

    /**
     * Inserts a new department into the database.
     *
     * @param obj the department to be inserted
     */
    void insert(Department obj);

    /**
     * Updates an existing department in the database.
     *
     * @param obj the department with updated data
     */
    void update(Department obj);

    /**
     * Deletes a department by its identifier.
     *
     * @param id the department id to be deleted
     */
    void deleteById(Integer id);

    /**
     * Finds a department by its identifier.
     *
     * @param id the department id to search for
     * @return the department found, or {@code null} if not found
     */
    Department findById(Integer id);

    /**
     * Returns all departments from the database.
     *
     * @return a list of all departments
     */
    List<Department> findAll();
}