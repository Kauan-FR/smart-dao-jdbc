package com.kauanferreira.smartdaojdbc.dao.impl;

import com.kauanferreira.smartdaojdbc.DB;
import com.kauanferreira.smartdaojdbc.dao.DaoFactory;
import com.kauanferreira.smartdaojdbc.dao.SellerDao;
import com.kauanferreira.smartdaojdbc.entity.Department;
import com.kauanferreira.smartdaojdbc.entity.Seller;
import org.junit.jupiter.api.*;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Integration tests for {@link SellerDaoJDBC}.
 * Tests all CRUD operations and custom queries against
 * a real PostgreSQL database.
 *
 * @author Kauan
 * @version 1.0
 * @since 2026
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SellerDaoJDBCTest {

    private static SellerDao sellerDao;
    private static Integer insertId;

    @BeforeAll
    public static void setUp() {
        sellerDao = DaoFactory.createSellerDao();
    }

    @AfterAll
    public static void tearDown() {
        DB.closePool();
    }

    @Test
    @Order(1)
    @DisplayName("Should insert a new seller and return generated id")
    public void insertShouldSetGeneratedId() {
        Seller seller = new Seller(
                null, "Test User", "testeuser@gmail.com", 2500.0,
                new Date(), new Department(1, null)
        );

        sellerDao.insert(seller);
        insertId = seller.getId();

        assertNotNull(seller.getId());
        assertTrue(seller.getId() > 0);
    }

    @Test
    @Order(2)
    @DisplayName("Should find seller by id")
    public void findByIdShouldReturnSeller() {
        Seller seller = sellerDao.findById(insertId);

        assertNotNull(seller);
        assertEquals("Test User", seller.getName());
        assertEquals("testeuser@gmail.com", seller.getEmail());
    }
}
