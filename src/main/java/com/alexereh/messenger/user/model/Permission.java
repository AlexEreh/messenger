package com.alexereh.messenger.user.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

	ADMIN_READ("admin:read"),
	ADMIN_UPDATE("admin:update"),
	ADMIN_CREATE("admin:create"),
	ADMIN_DELETE("admin:delete"),
	USER_READ_MESSAGES("user:read_messages"),
	USER_UPDATE("management:update"),
	USER_CREATE("management:create"),
	MANAGER_DELETE("management:delete");

	private final String permission;
}