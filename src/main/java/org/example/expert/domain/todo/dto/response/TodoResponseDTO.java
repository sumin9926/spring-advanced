package org.example.expert.domain.todo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.example.expert.domain.user.dto.response.UserResponseDTO;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TodoResponseDTO {

	private final Long id;
	private final String title;
	private final String contents;
	private final String weather;
	private final UserResponseDTO user;
	private final LocalDateTime createdAt;
	private final LocalDateTime modifiedAt;
}
