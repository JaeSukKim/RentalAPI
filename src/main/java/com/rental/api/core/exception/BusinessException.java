package com.rental.api.core.exception;

public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1640647214193797119L;

    public BusinessException(String message) {
        super(message);
    }
}

