package com.abhisek.mindtree.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abhisek.mindtree.constant.Constants;
import com.abhisek.mindtree.entity.ERole;
import com.abhisek.mindtree.entity.Role;
import com.abhisek.mindtree.entity.User;
import com.abhisek.mindtree.exception.EmailException;
import com.abhisek.mindtree.exception.NoRoleFoundException;
import com.abhisek.mindtree.jwt.AuthEntryPointJwt;
import com.abhisek.mindtree.jwt.JwtUtils;
import com.abhisek.mindtree.model.JwtResponse;
import com.abhisek.mindtree.model.LoginRequest;
import com.abhisek.mindtree.model.MessageApi;
import com.abhisek.mindtree.model.SignupRequest;
import com.abhisek.mindtree.repository.RoleRepository;
import com.abhisek.mindtree.repository.UserRepository;
import com.abhisek.mindtree.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication Controller", description = "(This is used for user registration and user login)")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	private JwtUtils jwtUtils;
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Login to the App", description = "Login to Shopping cart App", tags = { "login" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = LoginRequest.class))),
			@ApiResponse(responseCode = "401", description = "UnAuthorized") })
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		logger.error("Authenticate User method");
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}

	@PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Register to the App", description = "Register to Shopping cart App", tags = { "register" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = SignupRequest.class))),
			@ApiResponse(responseCode = "401", description = "UnAuthorized") })
	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			MessageApi api = MessageApi.builder().message(Constants.USERNAME_TAKEN).build();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(api);
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new EmailException();
		}

		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new NoRoleFoundException());
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new NoRoleFoundException());
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new NoRoleFoundException());
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new NoRoleFoundException());
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);
		MessageApi api = MessageApi.builder().message(Constants.USER_REGISTER_SUCCES).build();
		return ResponseEntity.status(HttpStatus.OK).body(api);
	}
}