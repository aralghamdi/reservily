package com.reservly.users.controller;

import com.reservly.users.model.dto.request.CreateUserRequestDto;
import com.reservly.users.model.dto.UserDto;
import com.reservly.users.service.UserService;
import com.reservly.users.util.enums.UserType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    ResponseEntity<UserDto> getUser(){
        return ResponseEntity.ok(userService.getUser());
    }

    @PostMapping("/create")
    ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserRequestDto request){
        return ResponseEntity.ok(userService.createUser(request));
    }


    @PostMapping("/init-admin")
    ResponseEntity<UserDto> initAdmin(){
        CreateUserRequestDto request = CreateUserRequestDto.builder()
                .email("admin@admin.com")
                .firstName("admin")
                .lastName("admin")
                .password("admin@123")
                .confirmPassword("admin@123")
                .type(UserType.ADMIN)
                .build();
        return ResponseEntity.ok(userService.createUser(request));
    }

}
