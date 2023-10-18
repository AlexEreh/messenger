package com.alexereh.messenger.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
		info = @Info(
				contact = @Contact(
						name = "Alexander Ereshkin",
						email = "xcode111@mail.ru"
				),
				description = "REST API мессенджера для тестового задания РЕЛЭКС",
				title = "BruhMessenger",
				version = "1.0"
		),
		servers = {
				@Server(
						description = "TEST ENV",
						url = "http://localhost:8080"
				)
		},
		security = {
				@SecurityRequirement(
						name = "bearerAuth"
				)
		}
)
@SecurityScheme(
		name = "bearerAuth",
		description = "JWT auth description",
		scheme = "bearer",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}