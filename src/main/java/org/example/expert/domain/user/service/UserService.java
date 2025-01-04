package org.example.expert.domain.user.service;

import lombok.RequiredArgsConstructor;

import org.example.expert.config.PasswordEncoder;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.user.dto.request.UserChangePasswordRequestDTO;
import org.example.expert.domain.user.dto.response.UserResponseDTO;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserResponseDTO findUserById(long userId) {
		User user = userRepository.findUserByIdOrElseThrow(userId);
		return UserResponseDTO.createDTO(user);
	}

	@Transactional
	public void changePassword(long userId, @Validated UserChangePasswordRequestDTO userChangePasswordRequest) {
		checkPasswordValid(userChangePasswordRequest);

		User user = userRepository.findUserByIdOrElseThrow(userId);

		validatePasswordUpdateRequest(userChangePasswordRequest, user);

		user.changePassword(passwordEncoder.encode(userChangePasswordRequest.getNewPassword()));
	}

	public void checkPasswordValid(UserChangePasswordRequestDTO userChangePasswordRequest) {
		if (userChangePasswordRequest.getNewPassword().length() < 8 ||
			!userChangePasswordRequest.getNewPassword().matches(".*\\d.*") ||
			!userChangePasswordRequest.getNewPassword().matches(".*[A-Z].*")) {
			throw new InvalidRequestException("새 비밀번호는 8자 이상이어야 하고, 숫자와 대문자를 포함해야 합니다.");
		}
	}

	public void validatePasswordUpdateRequest(UserChangePasswordRequestDTO userChangePasswordRequest, User user) {
		if (passwordEncoder.matches(userChangePasswordRequest.getNewPassword(), user.getPassword())) {
			throw new InvalidRequestException("새 비밀번호는 기존 비밀번호와 같을 수 없습니다.");
		}
		if (!passwordEncoder.matches(userChangePasswordRequest.getOldPassword(), user.getPassword())) {
			throw new InvalidRequestException("잘못된 비밀번호입니다.");
		}
	}

}
