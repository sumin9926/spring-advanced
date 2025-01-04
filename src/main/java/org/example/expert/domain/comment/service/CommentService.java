package org.example.expert.domain.comment.service;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;

import org.example.expert.domain.comment.dto.request.CommentSaveRequestDTO;
import org.example.expert.domain.comment.dto.response.*;
import org.example.expert.domain.common.dto.AuthUserDTO;
import org.example.expert.domain.comment.entity.Comment;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.comment.repository.CommentRepository;
import org.example.expert.domain.todo.repository.TodoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

	private final TodoRepository todoRepository;
	private final CommentRepository commentRepository;

	@Transactional
	public CommentSaveResponseDTO saveComment(AuthUserDTO authUser, long todoId,
		CommentSaveRequestDTO commentSaveRequest) {
		User user = User.fromAuthUser(authUser);
		Todo todo = todoRepository.findByIdOrElseThrow(todoId);

		Comment newComment = new Comment(commentSaveRequest.getContents(), user, todo);
		Comment savedComment = commentRepository.save(newComment);

		return CommentSaveResponseDTO.creatDTO(savedComment, user);
	}

	public List<CommentResponseDTO> getComments(long todoId) {
		List<Comment> commentList = commentRepository.findAllByTodoId(todoId);

		List<CommentResponseDTO> dtoList = new ArrayList<>();
		for (Comment comment : commentList) {
			dtoList.add(CommentResponseDTO.createDTO(comment));
		}
		return dtoList;
	}
}
