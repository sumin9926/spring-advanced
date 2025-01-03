package org.example.expert.domain.comment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.example.expert.domain.user.dto.response.UserResponseDTO;

@Getter
@AllArgsConstructor
public class CommentSaveResponseDTO {

	private final Long id;
	private final String contents;
	private final UserResponseDTO user;
}
