package org.example.expert.domain.user.controller;

import lombok.RequiredArgsConstructor;

import org.example.expert.domain.common.annotation.Auth;
import org.example.expert.domain.common.dto.AuthUserDTO;
import org.example.expert.domain.user.dto.request.UserChangePasswordRequestDTO;
import org.example.expert.domain.user.dto.response.UserResponseDTO;
import org.example.expert.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/users/{userId}")
	public ResponseEntity<UserResponseDTO> findUserById(@PathVariable long userId) {
		return ResponseEntity.ok(userService.findUserById(userId));
	}

	@PutMapping("/users")
	public void changePassword(@Auth AuthUserDTO authUser,
		@RequestBody UserChangePasswordRequestDTO userChangePasswordRequest) {
		userService.changePassword(authUser.getId(), userChangePasswordRequest);
	}
}
