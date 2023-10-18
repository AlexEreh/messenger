package com.alexereh.messenger.auth.requests;

import com.alexereh.messenger.user.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

	private String firstName;
	private String lastName;
	private String nickName;
	private String email;
	private String password;
	private Role role;
}