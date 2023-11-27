package com.demo.exceptions;

public class WrongCredentialsException extends RuntimeException{
	
	public WrongCredentialsException(String message) {
		super(message);
	}

}
