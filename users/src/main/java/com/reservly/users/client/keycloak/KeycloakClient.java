package com.reservly.users.client.keycloak;

import com.reservly.users.client.keycloak.Model.*;
import com.reservly.users.client.keycloak.Model.request.AssginRoleRequestDto;
import com.reservly.users.client.keycloak.Model.request.KcCreateUserRequestDto;
import com.reservly.users.client.keycloak.config.KeycloakFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "keycloak-client", url = "${keycloak.url}", configuration = KeycloakFeignConfig.class)
public interface KeycloakClient {

    @PostMapping(value = "/realms/${keycloak.realm}/protocol/openid-connect/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    KcTokenDto getToken(@RequestBody MultiValueMap<String, String> request);

    @PostMapping(value = "/admin/realms/${keycloak.realm}/users/", consumes = MediaType.APPLICATION_JSON_VALUE)
    feign.Response createUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody KcCreateUserRequestDto request);

    @GetMapping(value = "/admin/realms/${keycloak.realm}/roles/{role}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    KcRoleDto getRoleDetails(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable("role") String role);

    @PostMapping(value = "/admin/realms/${keycloak.realm}/users/{userId}/role-mappings/realm", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    void assignRole(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable("userId") String userId, @RequestBody List<AssginRoleRequestDto> request);
}
