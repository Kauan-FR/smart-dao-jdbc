package com.kauanferreira.smartdaojdbc.dao.impl;

import com.kauanferreira.smartdaojdbc.DB;
import com.kauanferreira.smartdaojdbc.dao.DaoFactory;
import com.kauanferreira.smartdaojdbc.dao.DepartmentDao;
import com.kauanferreira.smartdaojdbc.entity.Department;
import com.kauanferreira.smartdaojdbc.exception.EntityNotFoundException;
import org.junit.jupiter.api.*;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for {@link DepartmentDaoJDBC}.
 * Tests all CRUD operations against a real PostgreSQL database.
 *
 * @author Kauan
 * @version 1.0
 * @since 2026
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DepartmentDaoJDBCTest {

    private static DepartmentDao departmentDao;
    private static Integer insertedId;

    @BeforeAll
    public static void setUp() {
        departmentDao = DaoFactory.createDepartmentDao();
    }

    @AfterAll
    public static void tearDown() {
        DB.closePool();
    }

    @Test
    @Order(1)
    @DisplayName("Should insert a new department and return generated id")
    public void insertShouldSetGeneratedId() {
        Department department = new Department(null, "Test Department");

        departmentDao.insert(department);
        insertedId = department.getId();

        assertNotNull(department.getId());
        assertTrue(department.getId() > 0);
    }

    @Test
    @Order(2)
    @DisplayName("Should find department by id")
    public void findByIdShouldReturnDepartment() {
        Department department = departmentDao.findById(insertedId);

        assertNotNull(department);
        assertEquals("Test Department", department.getName());
    }

    @Test
    @Order(3)
    @DisplayName("Should throw EntityNotFoundException for invalid id")
    public void findByIdShouldThrowWhenNotFound() {
        assertThrows(EntityNotFoundException.class, () -> departmentDao.findById(9999999));
    }
}
