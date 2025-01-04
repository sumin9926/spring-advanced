package org.example.expert.domain.manager.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.example.expert.domain.manager.entity.Manager;
import org.example.expert.domain.user.dto.response.UserResponseDTO;

@Getter
@AllArgsConstructor
public class ManagerResponseDTO {

	private final Long id;
	private final UserResponseDTO user;

	public static ManagerResponseDTO createDTO(Manager manager) {
		return new ManagerResponseDTO(manager.getId(), UserResponseDTO.createDTO(manager.getUser()));
	}
}
