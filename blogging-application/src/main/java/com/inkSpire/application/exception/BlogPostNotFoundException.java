package com.inkSpire.application.exception;

import java.io.Serial;

public class BlogPostNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public BlogPostNotFoundException(String message) {
        super(message);
    }
}
