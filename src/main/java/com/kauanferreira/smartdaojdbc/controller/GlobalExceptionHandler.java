package com.kauanferreira.smartdaojdbc.controller;

import com.kauanferreira.smartdaojdbc.exception.DbException;
import com.kauanferreira.smartdaojdbc.exception.DbIntegrityException;
import com.kauanferreira.smartdaojdbc.exception.DuplicateEntryException;
import com.kauanferreira.smartdaojdbc.exception.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

/**
 * Global exception handler for the REST API.
 * Catches custom exceptions thrown by DAO layer and returns
 * standardized JSON error responses.
 *
 * @author Kauan
 * @version 1.0
 * @since 2026
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles entity not found exceptions.
     *
     * @param e       the exception thrown
     * @param request the HTTP request
     * @return 404 Not Found with error details
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError error = new StandardError(
                Instant.now(),
                status.value(),
                "Resource not found",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(error);
    }

    /**
     * Handles duplicate entry exceptions (e.g., unique constraint violation on email).
     *
     * @param e       the exception thrown
     * @param request the HTTP request
     * @return 409 Conflict with error details
     */
    @ExceptionHandler(DuplicateEntryException.class)
    public ResponseEntity<StandardError> duplicateEntry(DuplicateEntryException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        StandardError error = new StandardError(
                Instant.now(),
                status.value(),
                "Duplicate entry",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(error);
    }

    /**
     * Handles referential integrity violations (e.g., deleting a department with associated sellers).
     *
     * @param e       the exception thrown
     * @param request the HTTP request
     * @return 409 Conflict with error details
     */
    @ExceptionHandler(DbIntegrityException.class)
    public  ResponseEntity<StandardError> integrityViolation(DbIntegrityException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        StandardError error = new StandardError(
                Instant.now(),
                status.value(),
                "Referential integrity violation",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(error);
    }

    /**
     * Handles general database exceptions.
     *
     * @param e       the exception thrown
     * @param request the HTTP request
     * @return 500 Internal Server Error with error details
     */
    @ExceptionHandler(DbException.class)
    public ResponseEntity<StandardError> databaseError(DbException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        StandardError error = new StandardError(
                Instant.now(),
                status.value(),
                "Database error",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(error);
    }

    /**
     * Handles illegal argument exceptions (e.g., invalid id, null name, invalid email format).
     *
     * @param e       the exception thrown
     * @param request the HTTP request
     * @return 400 Bad Request with error details
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardError> badRequest(IllegalArgumentException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError error = new StandardError(
                Instant.now(),
                status.value(),
                "Invalid request",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(error);
    }
}
