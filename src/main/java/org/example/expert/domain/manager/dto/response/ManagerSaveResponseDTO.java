package org.example.expert.domain.manager.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.example.expert.domain.manager.entity.Manager;
import org.example.expert.domain.user.dto.response.UserResponseDTO;
import org.example.expert.domain.user.entity.User;

@Getter
@AllArgsConstructor
public class ManagerSaveResponseDTO {

	private final Long id;
	private final UserResponseDTO user;

	public static ManagerSaveResponseDTO createDTO(Manager manager, User user){
		return new ManagerSaveResponseDTO(
			manager.getId(),
			new UserResponseDTO(user.getId(), user.getEmail())
		);
	}
}
