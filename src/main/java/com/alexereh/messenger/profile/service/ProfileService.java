package com.alexereh.messenger.profile.service;

import com.alexereh.messenger.profile.responses.ProfileInfo;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class ProfileService {
	public ProfileInfo getMyInfo(Principal connectedUser) {
		return ProfileInfo.builder().build();
	}
}
