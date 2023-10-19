package com.alexereh.messenger.profile.service;

import com.alexereh.messenger.profile.responses.ProfileInfoResponse;
import com.alexereh.messenger.profile.requests.NewProfileInfoRequest;
import com.alexereh.messenger.user.model.User;
import com.alexereh.messenger.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class ProfileService {
	private final UserRepository repository;
	public ProfileInfoResponse getMyInfo(Principal connectedUser) {
		var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
		var userInStorage = repository.findById(user.getId()).orElseThrow();
		return new ProfileInfoResponse(userInStorage.getFirstName(), userInStorage.getLastName(), user.getId());
	}

	public void changeMyInfo(Principal connectedUser, NewProfileInfoRequest request){
		var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
		var userFromStorage = repository.findById(user.getId()).orElseThrow();
		userFromStorage.setFirstName(request.getFirstName());
		userFromStorage.setLastName(request.getLastName());
		repository.save(userFromStorage);
	}
}
