package com.reservly.users.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class KeycloakException extends RuntimeException {
    private int statusCode;
    private String error;
    @JsonProperty("error_description")
    private String errorDescription;
    private String errorMessage;
}