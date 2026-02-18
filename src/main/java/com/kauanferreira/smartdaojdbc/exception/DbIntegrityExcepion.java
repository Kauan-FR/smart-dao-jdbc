package com.kauanferreira.smartdaojdbc.exception;

public class DbIntegrityExcepion extends RuntimeException {
    public DbIntegrityExcepion(String message) {
        super(message);
    }
}
