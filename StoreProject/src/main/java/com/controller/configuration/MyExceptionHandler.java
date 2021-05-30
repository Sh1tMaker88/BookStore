package com.controller.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<IncorrectDataInput> unexpectedHandlerServiceException(NoSuchEntityException ex) {
        IncorrectDataInput incorrectDataInput = new IncorrectDataInput();
        incorrectDataInput.setInfo(ex.getMessage());
        return new ResponseEntity<>(incorrectDataInput, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<IncorrectDataInput> unexpectedHandlerServiceException(Exception ex) {
        IncorrectDataInput incorrectDataInput = new IncorrectDataInput();
        incorrectDataInput.setInfo(ex.getMessage());
        return new ResponseEntity<>(incorrectDataInput, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
