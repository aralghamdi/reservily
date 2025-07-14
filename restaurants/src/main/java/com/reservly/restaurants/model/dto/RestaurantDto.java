package com.reservly.restaurants.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RestaurantDto {
    private Long id;

    private String name;

    private CuisineDto cuisine;

    private CityDto city;

    private DistrictDto district;

    private List<TableDto> tables;

    private String openTime;

    private String closeTime;
}
