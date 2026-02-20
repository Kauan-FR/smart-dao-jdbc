package com.kauanferreira.smartdaojdbc.dao.impl;

import com.kauanferreira.smartdaojdbc.DB;
import com.kauanferreira.smartdaojdbc.dao.DepartmentDao;
import com.kauanferreira.smartdaojdbc.entity.Department;
import com.kauanferreira.smartdaojdbc.exception.DbException;
import com.kauanferreira.smartdaojdbc.exception.EntityNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC implementation of the {@link DepartmentDao} interface.
 * Handles all database operations for the {@link Department} entity
 * using raw JDBC with PostgreSQL.
 *
 * @author Kauan
 * @version 1.0
 * @since 2026
 */

public class DepartmentDaoJDBC implements DepartmentDao {

    /** Database connection used for all operations. */
    private Connection connection;

    /**
     * Creates a new DepartmentDaoJDBC with the given database connection.
     *
     * @param connection the active database connection
     */
    public DepartmentDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    /**
     * {@inheritDoc}
     *
     * <p>After insertion, the generated id is retrieved
     * and set back into the given {@link Department} object.</p>
     */
    @Override
    public void insert(Department obj) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO department (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setString(1, obj.getName());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();

                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(resultSet);
            } else {
                throw new DbException("No rows affected!");
            }
        } catch (SQLException e) {
            throw  new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>Updates the department name based on the given object's id.</p>
     */
    @Override
    public void update(Department obj) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE department SET name = ? WHERE id = ?"
            );
            preparedStatement.setString(1, obj.getName());
            preparedStatement.setInt(2, obj.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new  DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>If no department is found with the given id, no action is taken.</p>
     */
    @Override
    public void deleteById(Integer id) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM department WHERE id = ?"
            );
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>Retrieves the department directly from the department table
     * using a simple SELECT query.</p>
     */
    @Override
    public Department findById(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM department WHERE id = ?"
            );

            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return instantiateDepartment(resultSet);
            }
            throw new EntityNotFoundException("Department not found with id: " + id);
        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>Retrieves all departments ordered by database default.</p>
     */
    @Override
    public List<Department> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM department"
            );
            resultSet = preparedStatement.executeQuery();

            List<Department> departments = new ArrayList<>();

            while (resultSet.next()) {
                departments.add(instantiateDepartment(resultSet));
            }
            return departments;
        } catch (SQLException ex) {
            throw new  DbException(ex.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }

    @Override
    public List<Department> findAll(int page, int size) {
        return List.of();
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
        department.setId(resultSet.getInt("id"));
        department.setName(resultSet.getString("name"));
        return  department;
    }
}
