package com.reservly.users.service;

import com.reservly.users.model.dto.AccessTokenDto;
import com.reservly.users.model.dto.request.CreateUserRequestDto;
import com.reservly.users.model.dto.request.LoginRequestDto;


public interface KeycloakService {

    String createUser(CreateUserRequestDto request);

    AccessTokenDto login(LoginRequestDto request);
}
