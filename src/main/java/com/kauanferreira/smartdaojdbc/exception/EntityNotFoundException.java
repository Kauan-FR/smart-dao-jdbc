package com.kauanferreira.smartdaojdbc.exception;

/**
 * Thrown when an entity is not found in the database.
 *
 * @author Kauan
 * @version 1.0
 * @since 2026
 */

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
