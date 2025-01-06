package org.example.expert.domain.todo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.dto.response.UserResponseDTO;
import org.example.expert.domain.user.entity.User;

@Getter
@AllArgsConstructor
public class TodoSaveResponseDTO {

    private final Long id;
    private final String title;
    private final String contents;
    private final String weather;
    private final UserResponseDTO user;

    public static TodoSaveResponseDTO createDTO(Todo todo, String weather, User user){
        return new TodoSaveResponseDTO(
            todo.getId(),
            todo.getTitle(),
            todo.getContents(),
            weather,
            new UserResponseDTO(user.getId(), user.getEmail())
        );
    }
}
