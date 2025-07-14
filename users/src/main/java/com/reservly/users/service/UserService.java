package com.reservly.users.service;

import com.reservly.users.model.dto.request.CreateUserRequestDto;
import com.reservly.users.model.dto.UserDto;

public interface UserService {

    UserDto createUser(CreateUserRequestDto request);

    UserDto getUser();
}
