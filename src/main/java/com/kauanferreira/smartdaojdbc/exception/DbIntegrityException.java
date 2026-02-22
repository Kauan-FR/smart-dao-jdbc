package com.kauanferreira.smartdaojdbc.exception;

public class DbIntegrityException extends RuntimeException {
    public DbIntegrityException(String message) {
        super(message);
    }
}
