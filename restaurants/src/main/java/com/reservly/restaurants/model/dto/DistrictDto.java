package com.reservly.restaurants.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DistrictDto {
    private Long id;

    private String name;

    private String city;
}
