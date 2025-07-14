package com.reservly.users.service;

import com.reservly.users.model.dto.AccessTokenDto;
import com.reservly.users.model.dto.request.LoginRequestDto;

public interface AuthService {

    AccessTokenDto login(LoginRequestDto request);
}
