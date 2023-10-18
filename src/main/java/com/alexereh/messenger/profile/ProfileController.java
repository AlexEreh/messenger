package com.alexereh.messenger.profile;

import com.alexereh.messenger.profile.service.ProfileService;
import com.alexereh.messenger.user.service.UserService;
import com.alexereh.messenger.user.requests.ChangePasswordRequest;
import com.alexereh.messenger.user.responses.ChangePasswordResponse;
import com.alexereh.messenger.user.responses.DeleteUserResponse;
import com.alexereh.messenger.user.responses.IsUserDeletedResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
@Tag(name = "Profile Controller", description = "API для операций над профилем пользователя")
public class ProfileController {

	private final ProfileService service;
	@GetMapping("/get-my-info")
	public ResponseEntity<ChangePasswordResponse> getMyInfo(
			Principal connectedUser
	) {
		service.getMyInfo(connectedUser);
		return ResponseEntity.ok().build();
	}
}
