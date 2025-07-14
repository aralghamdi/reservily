package com.reservly.users.client.keycloak.Model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssginRoleRequestDto {
    private String id;
    private String name;
}
