package com.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.demo.exceptions.UserExistException;
import com.demo.exceptions.UserNotFoundException;
import com.demo.exceptions.WrongCredentialsException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> GlobalUserNotFoundException(UserNotFoundException ex) {
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(UserExistException.class)
    public ResponseEntity<Object> GlobalUserExistException(UserExistException ex) {
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(WrongCredentialsException.class)
    public ResponseEntity<Object> GlobalWrongCredentialsException(WrongCredentialsException ex) {
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
