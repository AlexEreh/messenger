package com.alexereh.messenger.auth;

import com.alexereh.messenger.auth.requests.LoginRequest;
import com.alexereh.messenger.auth.requests.RegisterRequest;
import com.alexereh.messenger.auth.responses.AuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller", description = "API для логина/регистрации.")
public class AuthenticationController {

	private final AuthenticationService service;

	@Operation(
			summary = "Регистрация пользователя",
			description = "Позволяет зарегистрировать пользователя"
	)
	@PostMapping("/register")
	public AuthenticationResponse register(
			@RequestBody RegisterRequest request
	) {
		return service.register(request);
	}

	@Operation(
			summary = "Аутентификация пользователя",
			description = "Позволяет зайти за какого-то пользователя"
	)
	@PostMapping("/authenticate")
	public AuthenticationResponse authenticate(
			@RequestBody LoginRequest request
	) {
		return service.authenticate(request);
	}

	@Operation(
			summary = "Обновление JWT по Refresh токену",
			description = "Позволяет по истечении предыдущего токена по нему получить новый токен"
	)
	@PostMapping("/refresh-token")
	public void refreshToken(
			HttpServletRequest request,
			HttpServletResponse response
	) throws IOException {
		service.refreshToken(request, response);
	}
}