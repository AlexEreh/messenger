package com.alexereh.messenger.exceptions;

public class NotSamePasswordsException extends RuntimeException{
	public NotSamePasswordsException(String message) {
		super(message);
	}
}
