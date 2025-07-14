package com.reservly.users.service.impl;

import com.reservly.users.client.keycloak.KeycloakClient;
import com.reservly.users.client.keycloak.Model.request.AssginRoleRequestDto;
import com.reservly.users.client.keycloak.Model.KcRoleDto;
import com.reservly.users.client.keycloak.Model.request.KcCreateUserRequestDto;
import com.reservly.users.client.keycloak.Model.KcTokenDto;
import com.reservly.users.client.keycloak.config.KeycloakProperties;
import com.reservly.users.mapper.GeneralMapper;
import com.reservly.users.model.dto.AccessTokenDto;
import com.reservly.users.model.dto.request.CreateUserRequestDto;
import com.reservly.users.model.dto.request.LoginRequestDto;
import com.reservly.users.service.KeycloakService;
import com.reservly.users.util.enums.UserType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Set;


@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {
    private final KeycloakClient keycloakClient;
    private final KeycloakProperties keycloakProperties;
    private final GeneralMapper generalMapper;

    @Override
    public String createUser(CreateUserRequestDto request) {
        String adminToken = getAdminToken();

        KcCreateUserRequestDto kcRequest = generalMapper.toKcCreateUserRequest(request);

        feign.Response response = keycloakClient.createUser(adminToken, kcRequest);

        String location = response.headers()
                .getOrDefault(HttpHeaders.LOCATION, Set.of())
                .stream()
                .findFirst().orElse("");
        String userId = location.substring(location.lastIndexOf('/') + 1);

        assignRole(userId, request.getType(), adminToken);

        return userId;
    }


    private void assignRole(String referenceId, UserType userType, String adminToken) {
        KcRoleDto kcRole = keycloakClient.getRoleDetails(adminToken, userType.name());

        keycloakClient.assignRole(getAdminToken(), referenceId, List.of(AssginRoleRequestDto.builder().id(kcRole.getId()).name(kcRole.getName()).build()));
    }


    @Override
    public AccessTokenDto login(LoginRequestDto request) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("username", request.getEmail());
        formData.add("password", request.getPassword());
        formData.add("client_id", keycloakProperties.getAuthClient());
        formData.add("client_secret", keycloakProperties.getAuthClientSecret());
        formData.add("grant_type", "password");
        formData.add("scope", "openid");

        KcTokenDto response = keycloakClient.getToken(formData);

        return AccessTokenDto.builder()
                .accessToken(String.format("%s %s", response.getTokenType(), response.getAccessToken()))
                .refreshToken(String.format("%s %s", response.getTokenType(), response.getRefreshToken()))
                .build();

    }

    private String getAdminToken(){
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", keycloakProperties.getAdminClient());
        formData.add("client_secret", keycloakProperties.getAdminClientSecret());
        formData.add("grant_type", "client_credentials");
        formData.add("scope", "openid");


        KcTokenDto response = keycloakClient.getToken(formData);
        return String.format("%s %s", response.getTokenType(), response.getAccessToken());
    }

}
