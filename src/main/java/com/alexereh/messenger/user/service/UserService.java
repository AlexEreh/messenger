package com.alexereh.messenger.user.service;

import com.alexereh.messenger.exceptions.NotSamePasswordsException;
import com.alexereh.messenger.exceptions.UserAlreadyDeletedException;
import com.alexereh.messenger.exceptions.WrongPasswordException;
import com.alexereh.messenger.user.repository.UserRepository;
import com.alexereh.messenger.user.model.User;
import com.alexereh.messenger.user.requests.ChangePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository repository;
	public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

		var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

		// check if the current password is correct
		if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
			throw new WrongPasswordException("Wrong password");
		}
		// check if the two new passwords are the same
		if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
			throw new NotSamePasswordsException("Password are not the same");
		}

		// update the password
		user.setPassword(passwordEncoder.encode(request.getNewPassword()));

		// save the new password
		repository.save(user);
	}

	public void deleteUser(Principal connectedUser) {
		var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
		var userFromStorage = repository.findById(user.getId()).orElseThrow();
		if (userFromStorage.isDeleted()) {
			throw new UserAlreadyDeletedException("Пользователь уже удалён");
		}
		user.setDeleted(true);
		repository.save(user);
	}

	public boolean isUserDeleted(Principal connectedUser) {
		var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
		return user.isDeleted();
	}
}