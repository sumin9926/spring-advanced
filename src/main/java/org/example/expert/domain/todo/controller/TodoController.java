package org.example.expert.domain.todo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.example.expert.domain.common.annotation.Auth;
import org.example.expert.domain.common.dto.AuthUserDTO;
import org.example.expert.domain.todo.dto.request.TodoSaveRequestDTO;
import org.example.expert.domain.todo.dto.response.TodoResponseDTO;
import org.example.expert.domain.todo.dto.response.TodoSaveResponseDTO;
import org.example.expert.domain.todo.service.TodoService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TodoController {

	private final TodoService todoService;

	@PostMapping("/todos")
	public ResponseEntity<TodoSaveResponseDTO> saveTodo(
		@Auth AuthUserDTO authUser,
		@Valid @RequestBody TodoSaveRequestDTO todoSaveRequest
	) {
		return ResponseEntity.ok(todoService.saveTodo(authUser, todoSaveRequest));
	}

	@GetMapping("/todos")
	public ResponseEntity<Page<TodoResponseDTO>> findAllTodos(
		@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		return ResponseEntity.ok(todoService.findAllTodos(page, size));
	}

	@GetMapping("/todos/{todoId}")
	public ResponseEntity<TodoResponseDTO> findTodoById(@PathVariable long todoId) {
		return ResponseEntity.ok(todoService.findTodoById(todoId));
	}
}
