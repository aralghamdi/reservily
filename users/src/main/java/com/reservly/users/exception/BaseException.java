package com.reservly.users.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;


@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException {
    private HttpStatusCode statusCode;
    private String message;
}
