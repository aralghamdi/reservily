package com.reservly.restaurants.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TableDto {
    private Long id;

    private Integer seatCount;

    private RestaurantDto restaurant;
}
