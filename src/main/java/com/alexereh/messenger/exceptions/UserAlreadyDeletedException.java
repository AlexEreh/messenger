package com.alexereh.messenger.exceptions;

public class UserAlreadyDeletedException extends RuntimeException{
	public UserAlreadyDeletedException(String message) {
		super(message);
	}
}
