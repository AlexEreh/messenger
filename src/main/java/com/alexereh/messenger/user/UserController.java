package com.alexereh.messenger.user;

import com.alexereh.messenger.user.requests.ChangePasswordRequest;
import com.alexereh.messenger.user.responses.ChangePasswordResponse;
import com.alexereh.messenger.user.responses.DeleteUserResponse;
import com.alexereh.messenger.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "API для операций пользователя")
public class UserController {

	private final UserService service;

	@Operation(
			summary = "Изменить пароль у текущего пользователя"
	)
	@PatchMapping("/change-password")
	public ResponseEntity<ChangePasswordResponse> changePassword(
			@RequestBody ChangePasswordRequest request,
			Principal connectedUser
	) {
		service.changePassword(request, connectedUser);
		return ResponseEntity.ok().build();
	}

	@Operation(
			summary = "Удалиться из этого жесткого бэкенда"
	)
	@DeleteMapping("/delete-self")
	public ResponseEntity<DeleteUserResponse> deleteUser(
			Principal connectedUser
	) {
		service.deleteUser(connectedUser);
		return ResponseEntity.ok(DeleteUserResponse.builder().build());
	}
}