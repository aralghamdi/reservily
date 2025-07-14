package com.reservly.users.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Objects;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@ControllerAdvice
@Slf4j
@Order(HIGHEST_PRECEDENCE + 1)
public class GlobalExceptionHandler {

    @ExceptionHandler(KeycloakException.class)
    public ResponseEntity<ProblemDetail> handelKeycloakException(KeycloakException e){
        String message = e.getError() != null ? e.getError() : e.getErrorMessage();
        log.error("Error: {}", message, e);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(e.getStatusCode()), message);
        if(e.getErrorDescription() != null){
            problemDetail.setProperty("description", e.getErrorDescription());
        }
        return ResponseEntity.status(e.getStatusCode()).body(problemDetail);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ProblemDetail> handelBaseException(BaseException e){
        log.error("Error: {}", e.getMessage(), e);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(e.getStatusCode(), e.getMessage());
        return ResponseEntity.status(e.getStatusCode()).body(problemDetail);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handelGenericException(Exception e){
        log.error("Error: {}", e.getMessage(), e);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handelValidationException(MethodArgumentNotValidException e){
        String message = getFirstError(e);
        log.error("Error: {}", message, e);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    private String getFirstError(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        return fieldErrors.stream()
                .map(fieldError -> String.format("Field '%s': %s", fieldError.getField(), fieldError.getDefaultMessage()))
                .toList().get(0);
    }


}
