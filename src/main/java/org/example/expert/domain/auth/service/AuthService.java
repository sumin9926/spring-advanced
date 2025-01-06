package org.example.expert.domain.auth.service;

import lombok.RequiredArgsConstructor;

import org.example.expert.config.JwtUtil;
import org.example.expert.config.PasswordEncoder;
import org.example.expert.domain.auth.dto.request.*;
import org.example.expert.domain.auth.dto.response.*;
import org.example.expert.domain.auth.exception.AuthException;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	@Transactional
	public SignupResponseDTO signup(SignupRequestDTO signupRequest) {

		userRepository.existsByEmailOrElseThrow(signupRequest.getEmail());

		String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());

		UserRole userRole = UserRole.of(signupRequest.getUserRole());
		User newUser = new User(signupRequest.getEmail(), encodedPassword, userRole);
		User savedUser = userRepository.save(newUser);

		String bearerToken = jwtUtil.createToken(savedUser.getId(), savedUser.getEmail(), userRole);

		return new SignupResponseDTO(bearerToken);
	}

	public SigninResponseDTO signin(SigninRequestDTO signinRequest) {
		User user = userRepository.findByEmailOrElseThrow(signinRequest.getEmail());

		validatePassword(signinRequest, user);

		String bearerToken = jwtUtil.createToken(user.getId(), user.getEmail(), user.getUserRole());

		return new SigninResponseDTO(bearerToken);
	}

	private void validatePassword(SigninRequestDTO signinRequest, User user) {
		if (!passwordEncoder.matches(signinRequest.getPassword(), user.getPassword())) {
			throw new AuthException("잘못된 비밀번호입니다.");
		}
	}

}
