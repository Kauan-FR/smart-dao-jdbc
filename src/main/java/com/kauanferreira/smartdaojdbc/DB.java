package com.kauanferreira.smartdaojdbc;

import com.kauanferreira.smartdaojdbc.exception.DbException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Utility class for managing PostgreSQL database connections.
 * Uses HikariCP connection pool for high-performance
 * database access in production environments.
 *
 * <p>Settings are loaded from the db.properties file,
 * including pool configuration parameters.</p>
 *
 * @author Kauan
 * @version 2.0
 * @since 2026
 */
public class DB {

    /** HikariCP data source for managing the connection pool. */
    private static HikariDataSource dataSource;

    /**
     * Returns the HikariCP DataSource instance.
     * Used by Spring Boot for Flyway migrations.
     *
     * @return the HikariDataSource managing the connection pool
     */
    public static HikariDataSource getDataSource() {
        return dataSource;
    }

    static {
        try {
            Properties props = loadProperties();

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(props.getProperty("dburl"));
            config.setUsername(props.getProperty("user"));
            config.setPassword(props.getProperty("password"));
            config.setMaximumPoolSize(Integer.parseInt(props.getProperty("maximumPoolSize", "10")));
            config.setMinimumIdle(Integer.parseInt(props.getProperty("minimumIdle", "5")));
            config.setConnectionTimeout(Long.parseLong(props.getProperty("connectionTimeout", "30000")));
            config.setIdleTimeout(Long.parseLong(props.getProperty("idleTimeout", "600000")));

            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            throw new DbException("Failed to initialize connection pool: " + e.getMessage());
        }
    }

    /**
     * Gets a connection from the HikariCP connection pool.
     *
     * @return Connection a database connection from the pool
     * @throws DbException if an error occurs while obtaining the connection
     */
    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    /**
     * Shuts down the HikariCP connection pool.
     * Should be called when the application is closing
     * to release all database resources.
     */
    public static void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

    /**
     * Safely closes a Connection object.
     * Returns the connection back to the pool instead of closing it permanently.
     *
     * @param connection the Connection to be returned to the pool, can be null
     * @throws DbException if an error occurs while closing the Connection
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
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
            } catch (SQLException e) {
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
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    /**
     * Loads connection properties from the db.properties file.
     *
     * @return Properties object containing the connection and pool settings
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
}