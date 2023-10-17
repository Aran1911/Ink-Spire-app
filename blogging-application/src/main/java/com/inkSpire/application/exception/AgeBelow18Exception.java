package com.inkSpire.application.exception;

public class AgeBelow18Exception extends RuntimeException {
    public AgeBelow18Exception(String message) {
        super(message);
    }
}
