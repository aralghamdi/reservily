package com.reservly.restaurants.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class TableRequestDto {
    @NotNull(message = "seatCount must be provided")
    @Min(1)
    private Integer seatCount;
}
