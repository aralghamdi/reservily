package com.reservly.restaurants.model.dto.request;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class CreateRestaurantRequestDto {
    @NotEmpty(message = "name must be provided")
    private String name;

    @NotNull(message = "cuisineId must be provided")
    private Long cuisineId;

    @NotNull(message = "cityId must be provided")
    private Long cityId;

    @NotNull(message = "districtId must be provided")
    private Long districtId;

    @NotNull(message = "openTime must be provided")
    private LocalTime openTime;

    @NotNull(message = "closeTime must be provided")
    private LocalTime closeTime;
}
