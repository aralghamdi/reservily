package com.reservly.users.service.impl;

import com.reservly.users.exception.BaseException;
import com.reservly.users.mapper.GeneralMapper;
import com.reservly.users.model.dao.UserEntity;
import com.reservly.users.model.dto.request.CreateUserRequestDto;
import com.reservly.users.model.dto.UserDto;
import com.reservly.users.repository.UserRepository;
import com.reservly.users.service.KeycloakService;
import com.reservly.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.reservly.users.util.CommonUtil.getUsername;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final KeycloakService keycloakService;
    private final GeneralMapper generalMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDto createUser(CreateUserRequestDto request) {
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new BaseException(HttpStatus.CONFLICT, "Email already exist");
        }

        String kcReference = keycloakService.createUser(request);
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        UserEntity userEntity = generalMapper.toUserEntity(request, kcReference, hashedPassword);
        userRepository.save(userEntity);

        return generalMapper.toUserDto(userEntity);
    }

    @Override
    public UserDto getUser() {
        Optional<UserEntity> userEntity = userRepository.findByUsername(getUsername());

        if(userEntity.isEmpty()){
            throw new BaseException(HttpStatus.NOT_FOUND, "User not fond");
        }

        return generalMapper.toUserDto(userEntity.get());
    }
}
