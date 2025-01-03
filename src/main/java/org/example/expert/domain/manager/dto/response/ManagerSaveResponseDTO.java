package org.example.expert.domain.manager.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.example.expert.domain.user.dto.response.UserResponseDTO;

@Getter
@AllArgsConstructor
public class ManagerSaveResponseDTO {

	private final Long id;
	private final UserResponseDTO user;
}
