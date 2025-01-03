package org.example.expert.domain.manager.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.example.expert.domain.user.dto.response.UserResponseDTO;

@Getter
@AllArgsConstructor
public class ManagerResponseDTO {

	private final Long id;
	private final UserResponseDTO user;
}
