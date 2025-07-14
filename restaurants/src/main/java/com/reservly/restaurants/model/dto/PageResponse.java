package com.reservly.restaurants.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class PageResponse<T> {
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private List<T> items;
}
