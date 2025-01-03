package org.example.expert.domain.todo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.expert.domain.user.dto.response.UserResponseDTO;

@Getter
@AllArgsConstructor
public class TodoSaveResponseDTO {

    private final Long id;
    private final String title;
    private final String contents;
    private final String weather;
    private final UserResponseDTO user;
}
