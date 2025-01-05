package org.example.expert.domain.comment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.example.expert.domain.comment.entity.Comment;
import org.example.expert.domain.user.dto.response.UserResponseDTO;

@Getter
@AllArgsConstructor
public class CommentResponseDTO {

    private final Long id;
    private final String contents;
    private final UserResponseDTO user;

    public static CommentResponseDTO createDTO(Comment comment){
        return new CommentResponseDTO(
            comment.getId(),
            comment.getContents(),
            UserResponseDTO.createDTO(comment.getUser())
        );
    }
}
