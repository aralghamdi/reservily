package com.reservly.users.client.keycloak.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KcRoleDto {
    private String id;

    private String name;

    private String description;

    private boolean composite;

    private boolean clientRole;

    private String containerId;
}
