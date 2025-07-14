package com.reservly.restaurants.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CityDto {
    private Long id;

    private String name;

    List<DistrictDto> districts;
}
