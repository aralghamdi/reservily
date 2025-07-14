package com.reservly.users.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class LoginRequestDto {
    @Email(message = "email must be in valid format")
    @NotEmpty(message = "email must be provided")
    private String email;

    @NotEmpty(message = "password must be provided")
    private String password;
}
