package org.example.expert.domain.auth.dto.response;

import lombok.Getter;

@Getter
public class SigninResponseDTO {

	private final String bearerToken;

	public SigninResponseDTO(String bearerToken) {
		this.bearerToken = bearerToken;
	}
}
