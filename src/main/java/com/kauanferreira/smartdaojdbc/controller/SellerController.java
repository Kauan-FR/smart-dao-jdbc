package com.kauanferreira.smartdaojdbc.controller;

import com.kauanferreira.smartdaojdbc.dao.DaoFactory;
import com.kauanferreira.smartdaojdbc.entity.Department;
import com.kauanferreira.smartdaojdbc.dao.SellerDao;
import com.kauanferreira.smartdaojdbc.entity.Seller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * REST controller for {@link Seller} operations.
 * Provides endpoints for CRUD, search and paginated listing.
 *
 * @author Kauan
 * @version 1.0
 * @since 2026
 */
@RestController
@RequestMapping("/api/sellers")
public class SellerController {

    private final SellerDao sellerDao = DaoFactory.createSellerDao();

    /**
     * Returns all sellers.
     * Supports optional pagination with page and size parameters.
     *
     * @param page the page number (optional)
     * @param size the number of records per page (optional)
     * @return list of sellers
     */
    @GetMapping
    public ResponseEntity<List<Seller>> findAll(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        if (page != null && size != null) {
            return ResponseEntity.ok(sellerDao.findAll(page, size));
        }
        return  ResponseEntity.ok(sellerDao.findAll());
    }

     /**
      * Finds a seller by id.
      *
      * @param id the seller id
      * @return the seller found
      */
     @GetMapping("/{id}")
     public ResponseEntity<Seller> findById(@PathVariable Integer id) {
         return ResponseEntity.ok(sellerDao.findById(id));
     }

    /**
     * Finds a seller by email.
     *
     * @param email the seller email
     * @return the seller found
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<Seller> findByEmail(@PathVariable String email) {
        return ResponseEntity.ok(sellerDao.findByEmail(email));
    }

    /**
     * Finds sellers by name using case-insensitive partial matching (ILIKE).
     *
     * @param name the name or partial name to search
     * @return list of matching sellers
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Seller>> findByName(@PathVariable String name) {
        return ResponseEntity.ok(sellerDao.findByName(name));
    }

    /**
     * Finds all sellers belonging to a specific department.
     *
     * @param departmentId the department id
     * @return list of sellers in the department
     */
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<Seller>> findByDepartment(@PathVariable Integer departmentId) {
        Department department = new Department();
        department.setId(departmentId);
        return ResponseEntity.ok(sellerDao.findByDepartment(department));
    }

    /**
     * Finds sellers born in a specific month.
     *
     * @param month the birth month (1-12)
     * @return list of sellers born in the given month
     */
    @GetMapping("/birth-month/{month}")
    public ResponseEntity<List<Seller>> findByBirthMonth(@PathVariable Integer month) {
        return ResponseEntity.ok(sellerDao.findByBirthMonth(month));
    }

    /**
     * Inserts a new seller.
     *
     * @param seller the seller to insert
     * @return the inserted seller with generated id
     */
    @PostMapping
    public ResponseEntity<Seller> insert(@RequestBody Seller seller) {
        sellerDao.insert(seller);
        return ResponseEntity.status(HttpStatus.CREATED).body(seller);
    }

    /**
     * Updates an existing seller.
     *
     * @param id     the seller id
     * @param seller the seller with updated data
     * @return the updated seller
     */
    @PutMapping("/{id}")
    public ResponseEntity<Seller> update(@PathVariable Integer id, @RequestBody Seller seller) {
        seller.setId(id);
        sellerDao.update(seller);
        return ResponseEntity.ok(seller);
    }

    /**
     * Deletes a seller by id.
     *
     * @param id the seller id
     * @return no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        sellerDao.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
