package com.alexereh.messenger.profile;

import com.alexereh.messenger.profile.responses.ProfileInfoResponse;
import com.alexereh.messenger.profile.requests.NewProfileInfoRequest;
import com.alexereh.messenger.profile.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
@Tag(name = "Profile Controller", description = "API для операций над профилем пользователя")
public class ProfileController {

	private final ProfileService service;

	@Operation(
			summary = "Получить информацию о текущем пользователе"
	)
	@GetMapping("/get-my-info")
	public ProfileInfoResponse getMyInfo(
			Principal connectedUser
	) {
		return service.getMyInfo(connectedUser);
	}

	@Operation(
			summary = "Изменить информацию о текущем пользователе"
	)
	@PatchMapping("/change-my-info")
	public void changeMyInfo(
			Principal connectedUser,
			@RequestBody NewProfileInfoRequest request
	) {
		service.changeMyInfo(connectedUser, request);
	}
}
