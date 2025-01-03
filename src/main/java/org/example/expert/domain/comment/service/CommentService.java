package org.example.expert.domain.comment.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.comment.dto.request.CommentSaveRequestDTO;
import org.example.expert.domain.comment.dto.response.CommentResponseDTO;
import org.example.expert.domain.comment.dto.response.CommentSaveResponseDTO;
import org.example.expert.domain.comment.entity.Comment;
import org.example.expert.domain.comment.repository.CommentRepository;
import org.example.expert.domain.common.dto.AuthUserDTO;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.todo.repository.TodoRepository;
import org.example.expert.domain.user.dto.response.UserResponseDTO;
import org.example.expert.domain.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final TodoRepository todoRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentSaveResponseDTO saveComment(AuthUserDTO authUser, long todoId, CommentSaveRequestDTO commentSaveRequest) {
        User user = User.fromAuthUser(authUser);
        Todo todo = todoRepository.findById(todoId).orElseThrow(() ->
                new InvalidRequestException("Todo not found"));

        Comment newComment = new Comment(
                commentSaveRequest.getContents(),
                user,
                todo
        );

        Comment savedComment = commentRepository.save(newComment);

        return new CommentSaveResponseDTO(
                savedComment.getId(),
                savedComment.getContents(),
                new UserResponseDTO(user.getId(), user.getEmail())
        );
    }

    public List<CommentResponseDTO> getComments(long todoId) {
        List<Comment> commentList = commentRepository.findAllByTodoId(todoId);

        List<CommentResponseDTO> dtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            User user = comment.getUser();
            CommentResponseDTO dto = new CommentResponseDTO(
                    comment.getId(),
                    comment.getContents(),
                    new UserResponseDTO(user.getId(), user.getEmail())
            );
            dtoList.add(dto);
        }
        return dtoList;
    }
}
