package com.reservly.users.client.keycloak.Model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class KcCreateUserRequestDto {
    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private boolean enabled;
}
