package com.kauanferreira.smartdaojdbc.dao;

import com.kauanferreira.smartdaojdbc.entity.Seller;

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
}
