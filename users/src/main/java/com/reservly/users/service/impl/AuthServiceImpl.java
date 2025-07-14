package com.reservly.users.service.impl;

import com.reservly.users.model.dto.AccessTokenDto;
import com.reservly.users.model.dto.request.LoginRequestDto;
import com.reservly.users.service.AuthService;
import com.reservly.users.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final KeycloakService keycloakService;


    @Override
    public AccessTokenDto login(LoginRequestDto request) {
        return keycloakService.login(request);
    }
}
