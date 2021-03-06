package com.abhisek.mindtree.model;

import java.util.Set;

import javax.validation.constraints.*;

import io.swagger.v3.oas.annotations.media.Schema;

public class SignupRequest {
	@NotBlank
	@Size(min = 3, max = 20)
	@Schema(description = "Select a username for registration", example = "MyUSerName")
	private String username;
	@NotBlank
	@Size(max = 30)
	@Email
	@Schema(description = "Provide an email for registration", example = "abcd.@xyz.com")
	private String email;
	@Schema(description = "Provide roles for registration", example = "USER/ADMIN")
	private Set<String> role;

	@NotBlank
	@Size(min = 6, max = 10)
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<String> getRole() {
		return this.role;
	}

	public void setRole(Set<String> role) {
		this.role = role;
	}
}