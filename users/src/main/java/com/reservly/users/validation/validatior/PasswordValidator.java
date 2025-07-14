package com.reservly.users.validation.validatior;


import com.reservly.users.model.dto.request.CreateUserRequestDto;
import com.reservly.users.validation.PasswordValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PasswordValidator implements ConstraintValidator<PasswordValidation, CreateUserRequestDto> {

    @Override
    public boolean isValid(CreateUserRequestDto createUserRequestDto, ConstraintValidatorContext context) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,}$";

        boolean isValid = true;
        if(!createUserRequestDto.getPassword().matches(regex)) {
            isValid = false;
            addError(context, "createUserRequestDto.password",  "Password must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, one number, and one special character");
        }

        if(!createUserRequestDto.getPassword().equals(createUserRequestDto.getConfirmPassword())){
            isValid = false;
            addError(context, "createUserRequestDto.confirmPassword",  "confirm password not match password");
        }

        return isValid;
    }

    private void addError(ConstraintValidatorContext context, String propertyPath, String message) {
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(propertyPath)
                .addConstraintViolation();
    }
}
