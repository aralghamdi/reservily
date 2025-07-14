package com.reservly.restaurants.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ListAvailableTablesRequestDto {
    private Long districtId;

    private Long cuisineId;

    @NotNull(message = "dateTime must be provided")
    private LocalDateTime dateTime;

    @NotNull(message = "durationMinutes must be provided")
    private Long durationMinutes;
}
