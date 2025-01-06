package org.example.expert.domain.comment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.comment.entity.Comment;
import org.example.expert.domain.user.dto.response.UserResponseDTO;

@Getter
@AllArgsConstructor
public class CommentSaveResponseDTO {

	private final Long id;
	private final String contents;
	private final UserResponseDTO user;

	public static CommentSaveResponseDTO creatDTO(Comment comment, User user){
		return new CommentSaveResponseDTO(
			comment.getId(),
			comment.getContents(),
			UserResponseDTO.createDTO(user)
		);
	}
}
