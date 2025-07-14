package com.reservly.restaurants.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservationDto {
    private String referenceNo;

    private String startTime;

    private String endTime;

    private RestaurantDto restaurant;

    private String status;
}
