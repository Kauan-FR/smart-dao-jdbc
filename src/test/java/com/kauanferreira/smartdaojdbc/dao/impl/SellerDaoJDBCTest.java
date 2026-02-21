package com.kauanferreira.smartdaojdbc.dao.impl;

import com.kauanferreira.smartdaojdbc.DB;
import com.kauanferreira.smartdaojdbc.dao.DaoFactory;
import com.kauanferreira.smartdaojdbc.dao.SellerDao;
import com.kauanferreira.smartdaojdbc.entity.Department;
import com.kauanferreira.smartdaojdbc.entity.Seller;
import com.kauanferreira.smartdaojdbc.exception.EntityNotFoundException;
import org.junit.jupiter.api.*;

import java.util.Date;
import java.util.List;

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
                null, "Test User", "testuser@gmail.com", 2500.0,
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
        assertEquals("testuser@gmail.com", seller.getEmail());
    }

    @Test
    @Order(3)
    @DisplayName("Should throw EntityNotFoundException for invalid id")
    public void findByIdShouldThrowWhenNotFound() {
        assertThrows(EntityNotFoundException.class, () -> {
            sellerDao.findById(999999999);
        });
    }

    @Test
    @Order(4)
    @DisplayName("Should find seller by email")
    public void findByEmailShouldReturnSeller() {
        Seller seller = sellerDao.findByEmail("testuser@gmail.com");

        assertNotNull(seller);
        assertEquals("Test User", seller.getName());
    }

    @Test
    @Order(5)
    @DisplayName("Should throw EntityNotFoundException for invalid email")
    public void findByEmailShouldThrowWhenNotFound() {
        assertThrows(EntityNotFoundException.class, () -> {
            sellerDao.findByEmail("nonexistent@gmail.com");
        });
    }

    @Test
    @Order(6)
    @DisplayName("Should find sellers by name containing keyword")
    public void findByNameShouldReturnMatchingSellers() {
        List<Seller> sellers = sellerDao.findByName("Test");

        assertFalse(sellers.isEmpty());
        assertTrue(sellers.stream().anyMatch(s -> s.getName().contains("Test")));
    }
}
