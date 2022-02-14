package com.abhisek.mindtree.model;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;

public class LoginRequest {
	@NotBlank
	@Schema(description = "Use the username provided during registration",example = "MyUSerName")
	private String username;

	@NotBlank
	@Schema(description = "Use the password provided during registration",example = "password")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}