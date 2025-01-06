package org.example.expert.domain.user.dto.response;

import lombok.Getter;

@Getter
public class UserSaveResponseDTO {

	private final String bearerToken;

	public UserSaveResponseDTO(String bearerToken) {
		this.bearerToken = bearerToken;
	}
}
