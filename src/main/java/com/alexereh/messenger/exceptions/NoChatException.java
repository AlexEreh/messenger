package com.alexereh.messenger.exceptions;

public class NoChatException extends RuntimeException {
	public NoChatException(String message) {
		super(message);
	}
}
