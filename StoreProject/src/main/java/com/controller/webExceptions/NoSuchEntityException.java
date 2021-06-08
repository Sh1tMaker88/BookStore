package com.controller.webExceptions;

public class NoSuchEntityException extends RuntimeException {

    public NoSuchEntityException(String message) {
        super(message);
    }
}
