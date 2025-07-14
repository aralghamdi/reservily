package com.reservly.users.model.dto.request;

import com.reservly.users.validation.PasswordValidation;
import com.reservly.users.util.enums.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@PasswordValidation
@Builder
public class CreateUserRequestDto {
    @Email(message = "email must be in valid format")
    @NotEmpty(message = "email must be provided")
    private String email;

    @NotEmpty(message = "firstName must be provided")
    private String firstName;

    @NotEmpty(message = "lastName must be provided")
    private String lastName;

    @NotNull(message = "type must be provided")
    private UserType type;

    @NotEmpty(message = "password must be provided")
    private String password;

    @NotEmpty(message = "confirmPassword must be provided")
    private String confirmPassword;
}
