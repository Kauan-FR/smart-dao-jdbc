package com.kauanferreira.smartdaojdbc.dao;

import com.kauanferreira.smartdaojdbc.entity.Department;
import com.kauanferreira.smartdaojdbc.entity.Seller;

import java.util.Date;
import java.util.List;

/**
 * Data Access Object interface for {@link Seller} entity.
 * Defines the standard CRUD operations to be implemented
 * by any persistence mechanism.
 *
 * @author Kauan
 * @version 1.0
 * @since 2026
 */
public interface SellerDao {

    /**
     * Inserts a new seller into the database.
     *
     * @param obj the seller to be inserted
     */
    void insert(Seller obj);

    /**
     * Updates an existing seller in the database.
     *
     * @param obj the seller with updated data
     */
    void update(Seller obj);

    /**
     * Deletes a seller by its identifier.
     *
     * @param id the seller id to be deleted
     */
    void deleteById(Integer id);

    /**
     * Finds a seller by its identifier.
     *
     * @param id the seller id to search for
     * @return the seller found, or {@code null} if not found
     */
    Seller findById(Integer id);

    /**
     * Returns all sellers from the database.
     *
     * @return a list of all sellers
     */
    List<Seller> findAll();

    /**
     * Finds all sellers belonging to a specific department.
     *
     * @param department the department to filter by
     * @return a list of sellers in the given department, ordered by name
     */
    List<Seller> findByDepartment(Department department);

    /**
     * Finds all sellers whose name contains the given string (case-insensitive).
     *
     * @param name the name or partial name to search for
     * @return a list of sellers matching the criteria, ordered by name
     */
    List<Seller> findByName(String name);

    /**
     * Finds a seller by their exact email address.
     *
     * @param email the email to search for
     * @return the seller found, or {@code null} if not found
     */
    List<Seller> findByEmail(String email);

    /**
     * Finds all sellers born in a specific month.
     * Useful for business scenarios such as identifying employees
     * with birthdays in a given month for company celebrations.
     *
     * @param month the birth month (1-12)
     * @return a list of sellers born in the given month, ordered by name
     */
    List<Seller> findByBirthMonth(int month);
}
