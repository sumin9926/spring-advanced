package org.example.expert.domain.user.repository;

import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

	default User findByEmailOrElseThrow(String email) {
		return findByEmail(email).orElseThrow(() -> new InvalidRequestException("가입되지 않은 유저입니다."));
	}

	boolean existsByEmail(String email);

	default void existsByEmailOrElseThrow(String email) {
		if (existsByEmail(email)) {
			throw new InvalidRequestException("이미 존재하는 이메일입니다.");
		}
	}

	default User findUserByIdOrElseThrow(long userId) {
		return findById(userId).orElseThrow(() -> new InvalidRequestException("User not found"));
	}

	default User findUserByIdOrElseThrow(long userId, String message) {
		return findById(userId).orElseThrow(() -> new InvalidRequestException(message));
	}
}
