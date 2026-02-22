package com.kauanferreira.smartdaojdbc.controller;

import com.kauanferreira.smartdaojdbc.dao.DaoFactory;
import com.kauanferreira.smartdaojdbc.dao.DepartmentDao;
import com.kauanferreira.smartdaojdbc.entity.Department;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for {@link Department} operations.
 * Provides endpoints for CRUD and paginated listing.
 *
 * @author Kauan
 * @version 1.0
 * @since 2026
 */
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

    /**
     * Returns all departments.
     * Supports optional pagination with page and size parameters.
     *
     * @param page the page number (optional)
     * @param size the number of records per page (optional)
     * @return list of departments
     */
    @GetMapping
    public ResponseEntity<List<Department>> findAll(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        if (page != null && size != null) {
            return ResponseEntity.ok(departmentDao.findAll(page, size));
        }
        return  ResponseEntity.ok(departmentDao.findAll());
    }

    /**
     * Finds a department by id.
     *
     * @param id the department id
     * @return the department found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Department> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(departmentDao.findById(id));
    }

    /**
     * Inserts a new department.
     *
     * @param department the department to insert
     * @return the inserted department with generated id
     */
    @PostMapping
    public ResponseEntity<Department> insert(@RequestBody Department department) {
        departmentDao.insert(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(department);
    }

    /**
     * Updates an existing department.
     *
     * @param id         the department id
     * @param department the department with updated data
     * @return the updated department
     */
    @PutMapping("/{id}")
    public ResponseEntity<Department> update(@PathVariable Integer id, @RequestBody Department department) {
        department.setId(id);
        departmentDao.update(department);
        return ResponseEntity.ok(department);
    }

    /**
     * Deletes a department by id.
     *
     * @param id the department id
     * @return no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        departmentDao.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
