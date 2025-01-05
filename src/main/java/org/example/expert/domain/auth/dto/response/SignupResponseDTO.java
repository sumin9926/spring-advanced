package org.example.expert.domain.auth.dto.response;

import lombok.Getter;

@Getter
public class SignupResponseDTO {

	private final String bearerToken;

	public SignupResponseDTO(String bearerToken) {
		this.bearerToken = bearerToken;
	}
}
