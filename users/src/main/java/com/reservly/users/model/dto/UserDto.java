package com.reservly.users.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private String createdAt;

    private String updatedAt;
}
