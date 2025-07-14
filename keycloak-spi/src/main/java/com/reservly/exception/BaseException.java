package com.reservly.exception;

import jakarta.ws.rs.core.Response;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseException extends RuntimeException {
    private Response.Status statusCode;
    private String message;
}
