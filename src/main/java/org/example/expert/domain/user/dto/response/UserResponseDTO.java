package org.example.expert.domain.user.dto.response;

import org.example.expert.domain.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDTO {

    private final Long id;
    private final String email;

    public static UserResponseDTO createDTO(User user){
        return new UserResponseDTO(user.getId(), user.getEmail());
    }
}
