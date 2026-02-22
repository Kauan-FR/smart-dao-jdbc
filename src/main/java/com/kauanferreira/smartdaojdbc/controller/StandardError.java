package com.kauanferreira.smartdaojdbc.controller;

import java.awt.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * Standard error response body for REST API exceptions.
 * Provides a consistent JSON structure for all error responses.
 *
 * @author Kauan
 * @version 1.0
 * @since 2026
 */

public class StandardError implements Serializable {

    public static final long serialVersionUID = 1L;

    private Instant timestamp;
    private Integer status;
    private String error;
    private  String message;
    private String path;

    public StandardError() {
    }

    public StandardError(Instant timestamp, Integer status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
