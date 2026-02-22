package com.kauanferreira.smartdaojdbc;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Spring configuration class that exposes the existing HikariCP
 * connection pool as a Spring-managed DataSource bean.
 * This allows Flyway to use the same pool managed by {@link DB}.
 *
 * @author Kauan
 * @version 1.0
 * @since 2026
 */
@Configuration
public class DataSourceConfig {

    /**
     * Provides the HikariCP DataSource from {@link DB} to the Spring context.
     * Flyway and other Spring components will use this DataSource.
     *
     * @return the shared HikariDataSource instance
     */
    @Bean
    public DataSource dataSource() {
        return DB.getDataSource();
    }

    /**
     * Configures and executes Flyway database migrations on startup.
     * Uses the same DataSource managed by {@link DB}.
     *
     * @param dataSource the shared DataSource
     * @return the configured Flyway instance
     */
    @Bean
    public Flyway flyway(DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration")
                .load();
        flyway.migrate();
        return flyway;
    }
}
