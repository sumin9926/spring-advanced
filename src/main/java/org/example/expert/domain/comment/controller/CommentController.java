package org.example.expert.domain.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.comment.dto.request.CommentSaveRequestDTO;
import org.example.expert.domain.comment.dto.response.CommentResponseDTO;
import org.example.expert.domain.comment.dto.response.CommentSaveResponseDTO;
import org.example.expert.domain.comment.service.CommentService;
import org.example.expert.domain.common.annotation.Auth;
import org.example.expert.domain.common.dto.AuthUserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/todos/{todoId}/comments")
    public ResponseEntity<CommentSaveResponseDTO> saveComment(
            @Auth AuthUserDTO authUser,
            @PathVariable long todoId,
            @Valid @RequestBody CommentSaveRequestDTO commentSaveRequest
    ) {
        return ResponseEntity.ok(commentService.saveComment(authUser, todoId, commentSaveRequest));
    }

    @GetMapping("/todos/{todoId}/comments")
    public ResponseEntity<List<CommentResponseDTO>> getComments(@PathVariable long todoId) {
        return ResponseEntity.ok(commentService.getComments(todoId));
    }
}
