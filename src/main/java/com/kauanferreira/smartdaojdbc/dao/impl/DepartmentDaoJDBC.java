package com.kauanferreira.smartdaojdbc.dao.impl;

import com.kauanferreira.smartdaojdbc.DB;
import com.kauanferreira.smartdaojdbc.dao.DepartmentDao;
import com.kauanferreira.smartdaojdbc.entity.Department;
import com.kauanferreira.smartdaojdbc.exception.DbException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    /** {@inheritDoc} */
    @Override
    public void insert(Department obj) {

    }

    /** {@inheritDoc} */
    @Override
    public void update(Department obj) {

    }

    /** {@inheritDoc} */
    @Override
    public void deleteById(Integer id) {

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
            return null;
        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }

    /** {@inheritDoc} */
    @Override
    public List<Department> findAll() {
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
