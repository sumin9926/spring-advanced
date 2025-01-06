package org.example.expert.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.auth.dto.request.SigninRequestDTO;
import org.example.expert.domain.auth.dto.request.SignupRequestDTO;
import org.example.expert.domain.auth.dto.response.SigninResponseDTO;
import org.example.expert.domain.auth.dto.response.SignupResponseDTO;
import org.example.expert.domain.auth.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/signup")
    public SignupResponseDTO signup(@Valid @RequestBody SignupRequestDTO signupRequest) {
        return authService.signup(signupRequest);
    }

    @PostMapping("/auth/signin")
    public SigninResponseDTO signin(@Valid @RequestBody SigninRequestDTO signinRequest) {
        return authService.signin(signinRequest);
    }
}
