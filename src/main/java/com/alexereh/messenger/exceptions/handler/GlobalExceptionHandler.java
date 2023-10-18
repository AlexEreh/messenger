package com.alexereh.messenger.exceptions.handler;

import com.alexereh.messenger.exceptions.ResourceNotFoundException;
import com.alexereh.messenger.exceptions.UserDeletedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler
	public ResponseEntity<AppError> catchUserDeletedException(UserDeletedException e) {
		log.error(e.getMessage(), e);
		return new ResponseEntity<>(
				new AppError(
						HttpStatus.GONE.value(),
						"Операция не была выполнена, так как пользователь удалён",
						e.getMessage()
				), HttpStatus.GONE
		);
	}

	@ExceptionHandler
	public ResponseEntity<AppError> catchResourceNotFoundException(ResourceNotFoundException e) {
		log.error(e.getMessage(), e);
		return new ResponseEntity<>(
				new AppError(
						HttpStatus.NOT_FOUND.value(),
						"Ресурс не был найден на сервере",
						e.getMessage()
				), HttpStatus.NOT_FOUND
		);
	}

	@Override
	@NonNull
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex,
			@NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
		AppError error = new AppError(
				HttpStatus.BAD_REQUEST.value(),
				"Запрос был получен с искажённым содержимым JSON",
				ex.getMessage()
		);
		return new ResponseEntity<>(error, status);
	}


}
