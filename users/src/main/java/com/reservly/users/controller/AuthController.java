package com.reservly.users.controller;

import com.reservly.users.model.dto.AccessTokenDto;
import com.reservly.users.model.dto.request.LoginRequestDto;
import com.reservly.users.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    ResponseEntity<AccessTokenDto> login(@Valid @RequestBody LoginRequestDto request){
        return ResponseEntity.ok(authService.login(request));
    }
}
