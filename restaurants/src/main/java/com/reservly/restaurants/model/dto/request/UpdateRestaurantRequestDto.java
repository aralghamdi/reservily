package com.reservly.restaurants.model.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class UpdateRestaurantRequestDto {
    private String name;

    private Long cuisineId;

    private Long cityId;

    private Long districtId;

    private LocalTime openTime;

    private LocalTime closeTime;
}
