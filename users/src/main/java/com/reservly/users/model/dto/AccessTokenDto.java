package com.reservly.users.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AccessTokenDto {
    private String accessToken;

    private String refreshToken;
}
