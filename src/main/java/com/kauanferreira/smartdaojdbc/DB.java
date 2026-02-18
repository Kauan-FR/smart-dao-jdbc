package com.kauanferreira.smartdaojdbc;

import com.kauanferreira.smartdaojdbc.exception.DbException;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * Utility class for managing PostgreSQL database connections.
 * Uses the Singleton pattern to maintain a single active connection.
 * Settings are loaded from the db.properties file.
 *
 * @author Kauan
 * @version 1.0
 * @since 2026
 */
public class DB {

    /** Single connection (Singleton) to the database. */
    private static Connection connection = null;

    /**
     * Gets the database connection.
     * If the connection does not exist yet, creates a new one using
     * the properties from db.properties. If it already exists, returns the same connection.
     *
     * @return Connection active database connection object
     * @throws DbException if an error occurs while establishing the connection
     */
    public static Connection getConnection() {
        if (connection == null) {
            try {
                Properties props = loadProperties();
                String url = props.getProperty("dburl");
                connection = DriverManager.getConnection(url, props);
            } catch (Exception e) {
                throw new DbException(e.getMessage());
            }
        }
        return connection;
    }

    /**
     * Loads connection properties from the db.properties file.
     * The file must be located in src/main/resources/ and contain:
     * <ul>
     *     <li>user - database user</li>
     *     <li>password - database password</li>
     *     <li>dburl - JDBC connection URL (e.g.: jdbc:postgresql://localhost:5432/livraria)</li>
     * </ul>
     *
     * @return Properties object containing the connection settings
     * @throws DbException if the db.properties file is not found or cannot be read
     */
    private static Properties loadProperties() {
        try (InputStream fs = DB.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

    /**
     * Closes the active database connection.
     * After closing, the connection is set to null to allow
     * a new connection in the future if needed.
     *
     * @throws DbException if an error occurs while closing the connection
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (Exception e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    /**
     * Safely closes a Statement object.
     * Checks if the Statement is not null before attempting to close it.
     *
     * @param stmt the Statement to be closed, can be null
     * @throws DbException if an error occurs while closing the Statement
     */
    public static void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    /**
     * Safely closes a ResultSet object.
     * Checks if the ResultSet is not null before attempting to close it.
     *
     * @param rs the ResultSet to be closed, can be null
     * @throws DbException if an error occurs while closing the ResultSet
     */
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                throw new DbException(e.getMessage());
            }
        }
    }
}