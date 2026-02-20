package com.kauanferreira.smartdaojdbc.exception;

/**
 * Thrown when attempting to insert a duplicate record
 * that violates a unique constraint in the database.
 *
 * @author Kauan
 * @version 1.0
 * @since 2026
 */

public class DuplicateEntryException extends RuntimeException {
    public DuplicateEntryException(String message) {
        super(message);
    }
}
