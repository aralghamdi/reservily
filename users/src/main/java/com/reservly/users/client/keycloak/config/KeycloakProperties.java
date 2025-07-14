package com.reservly.users.client.keycloak.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ConfigurationProperties(prefix = "keycloak")
@Configuration
public class KeycloakProperties {
    private String url;
    private String adminClient;
    private String adminClientSecret;
    private String authClient;
    private String authClientSecret;
}
