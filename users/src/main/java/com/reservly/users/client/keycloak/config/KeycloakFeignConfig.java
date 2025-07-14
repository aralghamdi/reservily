package com.reservly.users.client.keycloak.config;

import com.reservly.users.config.FeignConfig;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Configuration
public class KeycloakFeignConfig extends FeignConfig {

   @Bean
    public ErrorDecoder keycloakFeignErrorDecoder() {
        return new KeycloakErrorDecoder();
    }

    @Bean
    public RequestInterceptor keycloakFeignRequestInterceptor() {
        return template -> template.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
    }
}
