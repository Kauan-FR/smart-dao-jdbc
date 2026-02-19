package com.kauanferreira.smartdaojdbc.dao.impl;

import com.kauanferreira.smartdaojdbc.DB;
import com.kauanferreira.smartdaojdbc.dao.SellerDao;
import com.kauanferreira.smartdaojdbc.entity.Department;
import com.kauanferreira.smartdaojdbc.entity.Seller;
import com.kauanferreira.smartdaojdbc.exception.DbException;

import java.sql.*;
import java.util.*;

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

    private Connection connection;

    public SellerDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    /**
     * {@inheritDoc}
     *
     * <p>After insertion, the generated id is retrieved
     * and set back into the given {@link Seller} object.</p>
     */
    @Override
    public void insert(Seller obj) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO seller " +
                            "(name, email, birthdate, basesalary, departmentid) " +
                            "VALUES " +
                            "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setString(1, obj.getName());
            preparedStatement.setString(2, obj.getEmail());
            preparedStatement.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            preparedStatement.setDouble(4, obj.getBaseSalary());
            preparedStatement.setInt(5, obj.getDepartment().getId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();

                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(resultSet);
            } else {
                throw new DbException("No lines were changed");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
        }
    }

    /**
     * Updates an existing seller in the database.
     * Sets all fields (name, email, birthdate, basesalary, departmentid)
     * based on the seller's ID.
     *
     * @param obj the {@link Seller} object containing the updated data.
     *            The ID must match an existing record in the database.
     * @throws DbException if a database access error occurs during the update
     * @see Seller
     * @see DB#closeStatement(Statement)
     */
    @Override
    public void update(Seller obj) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE seller " +
                            "SET name = ?, email = ?, birthdate = ?, basesalary = ?, departmentid = ? " +
                            "WHERE Id = ?"
            );
            preparedStatement.setString(1, obj.getName());
            preparedStatement.setString(2, obj.getEmail());
            preparedStatement.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            preparedStatement.setDouble(4, obj.getBaseSalary());
            preparedStatement.setInt(5, obj.getDepartment().getId());
            preparedStatement.setInt(6, obj.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
        }
    }

    /**
     * Deletes a seller from the database by their ID.
     * If no seller is found with the given ID, no action is taken.
     *
     * @param id the ID of the seller to be deleted
     * @throws DbException if a database access error occurs,
     *         such as a referential integrity violation
     * @see DB#closeStatement(Statement)
     */
    @Override
    public void deleteById(Integer id) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM seller WHERE id = ?"
            );
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw  new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>Performs an INNER JOIN with the department table
     * to retrieve the associated {@link Department} data.</p>
     */
    @Override
    public Seller findById(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT seller.*,department.Name as DepName " +
                            "FROM seller INNER JOIN department " +
                            "ON seller.DepartmentId = department.Id " +
                            "WHERE seller.Id = ?"
            );
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Department department = instantiateDepartment(resultSet);
                return instantiateSeller(resultSet, department);
            }
            return  null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>Retrieves all sellers ordered by name, using a {@link Map}
     * to avoid creating duplicate {@link Department} instances.</p>
     */
    @Override
    public List<Seller> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT seller.*, department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "ORDER BY Name"
            );

            resultSet = preparedStatement.executeQuery();

            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (resultSet.next()) {

                Department dept = map.get(resultSet.getInt("departmentid"));

                if (dept == null) {
                    dept = instantiateDepartment(resultSet);
                    map.put(resultSet.getInt("departmentid"), dept);
                }

                Seller seller = instantiateSeller(resultSet, dept);
                sellers.add(seller);
            }
            return sellers;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>Performs an INNER JOIN with the department table and uses a
     * {@link Map} to avoid creating duplicate {@link Department} instances
     * when multiple sellers share the same department.</p>
     */
    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT seller.*, department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "WHERE DepartmentId = ? "
                            + "ORDER BY Name"
            );

            preparedStatement.setInt(1, department.getId());
            resultSet = preparedStatement.executeQuery();

            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (resultSet.next()) {

                Department dept = map.get(resultSet.getInt("departmentid"));

                if (dept == null) {
                    dept = instantiateDepartment(resultSet);
                    map.put(resultSet.getInt("departmentid"), dept);
                }

                Seller seller = instantiateSeller(resultSet, dept);
                sellers.add(seller);
            }
            return sellers;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }

    /**
     * Creates a {@link Department} instance from the current ResultSet row.
     *
     * @param resultSet the result set positioned at a valid row
     * @return a new Department populated with database values
     * @throws SQLException if a database access error occurs
     */
    private Department instantiateDepartment(ResultSet resultSet) throws SQLException {
        Department department = new Department();
        department.setId(resultSet.getInt("departmentid"));
        department.setName(resultSet.getString("DepName"));
        return  department;
    }

    /**
     * Creates a {@link Seller} instance from the current ResultSet row.
     *
     * @param resultSet  the result set positioned at a valid row
     * @param department the associated department
     * @return a new Seller populated with database values
     * @throws SQLException if a database access error occurs
     */
    private Seller instantiateSeller(ResultSet resultSet, Department department) throws SQLException {
        Seller seller = new Seller();
        seller.setId(resultSet.getInt("id"));
        seller.setName(resultSet.getString("name"));
        seller.setEmail(resultSet.getString("email"));
        seller.setBirthDate(resultSet.getDate("birthdate"));
        seller.setBaseSalary(resultSet.getDouble("basesalary"));
        seller.setDepartment(department);
        return seller;
    }
}