package com.kauanferreira.smartdaojdbc.dao.impl;

import com.kauanferreira.smartdaojdbc.DB;
import com.kauanferreira.smartdaojdbc.dao.DaoFactory;
import com.kauanferreira.smartdaojdbc.dao.DepartmentDao;
import com.kauanferreira.smartdaojdbc.entity.Department;
import com.kauanferreira.smartdaojdbc.exception.EntityNotFoundException;
import org.junit.jupiter.api.*;


import java.util.List;

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

    @Test
    @Order(4)
    @DisplayName("Should return all departments")
    public void findAllShouldReturnNonEmptyList() {
        List<Department> departments = departmentDao.findAll();

        assertFalse(departments.isEmpty());
    }

    @Test
    @Order(5)
    @DisplayName("Should return pagination departments")
    public void findAllPaginatedShouldRespectPageSize() {
        List<Department> departments = departmentDao.findAll(1, 2);

        assertNotNull(departments);
        assertTrue(departments.size() <= 2);
    }

    @Test
    @Order(6)
    @DisplayName("Should update department data")
    public void updateShouldModifyDepartmentData() {
        Department department = departmentDao.findById(insertedId);
        department.setName("Updated Department");

        departmentDao.update(department);

        Department updated = departmentDao.findById(insertedId);
        assertEquals("Updated Department", updated.getName());
    }

    @Test
    @Order(7)
    @DisplayName("Should delete department by id")
    public void deleteByIdShouldRemoveDepartment() {
        departmentDao.deleteById(insertedId);

        assertThrows(EntityNotFoundException.class, () -> departmentDao.findById(insertedId));
    }
}
