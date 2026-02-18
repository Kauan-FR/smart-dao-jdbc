package com.kauanferreira.smartdaojdbc.dao.impl;

import com.kauanferreira.smartdaojdbc.dao.SellerDao;
import com.kauanferreira.smartdaojdbc.entity.Seller;

import java.util.List;

/**
 * JDBC implementation of the {@link SellerDao} interface.
 * Handles all database operations for the {@link Seller} entity
 * using raw JDBC with PostgreSQL.
 *
 * @author Kauan
 * @version 1.0
 * @since 2026
 */
public class SellerDaoJDBC implements SellerDao {

    /** {@inheritDoc} */
    @Override
    public void insert(Seller obj) {

    }

    /** {@inheritDoc} */
    @Override
    public void update(Seller obj) {

    }

    /** {@inheritDoc} */
    @Override
    public void deleteById(Integer id) {

    }

    /** {@inheritDoc} */
    @Override
    public Seller findById(Integer id) {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public List<Seller> findAll() {
        return List.of();
    }
}