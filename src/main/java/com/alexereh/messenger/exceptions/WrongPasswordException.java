package com.alexereh.messenger.exceptions;

public class WrongPasswordException extends RuntimeException{
	public WrongPasswordException(String message) {
		super(message);
	}
}
