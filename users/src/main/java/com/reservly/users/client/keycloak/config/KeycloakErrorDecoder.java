package com.reservly.users.client.keycloak.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservly.users.exception.KeycloakException;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;


public class KeycloakErrorDecoder implements ErrorDecoder {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String s, Response response) {
        try {
            KeycloakException ex;
            if(response.body() != null){
                ex = objectMapper.readValue(response.body().asInputStream(), KeycloakException.class);
            } else {
                ex = KeycloakException.builder().build();
            }
            ex.setStatusCode(response.status());
            throw ex;
        } catch (IOException e) {
            return new RuntimeException("Error parsing keycloak response", e);
        }
    }
}
