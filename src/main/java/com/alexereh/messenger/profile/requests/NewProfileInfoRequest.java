package com.alexereh.messenger.profile.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewProfileInfoRequest {
	private String firstName;
	private String lastName;
}
