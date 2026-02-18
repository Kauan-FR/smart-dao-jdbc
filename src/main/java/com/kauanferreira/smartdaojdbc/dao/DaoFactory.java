package com.kauanferreira.smartdaojdbc.dao;

import com.kauanferreira.smartdaojdbc.dao.impl.SellerDaoJDBC;
/**
 * Factory class responsible for creating DAO instances.
 * Uses the Factory pattern to decouple DAO interface
 * from its JDBC implementation.
 *
 * @author Kauan
 * @version 1.0
 * @since 2026
 */
public class DaoFactory {

    /**
     * Creates a new instance of {@link SellerDao}.
     *
     * @return a JDBC-based implementation of SellerDao
     */
    public static SellerDao createSellerDao() {
        return new SellerDaoJDBC();
    }
}
