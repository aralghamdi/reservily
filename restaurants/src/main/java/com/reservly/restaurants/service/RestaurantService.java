package com.reservly.restaurants.service;

import com.reservly.restaurants.model.dto.RestaurantDto;
import com.reservly.restaurants.model.dto.TableDto;
import com.reservly.restaurants.model.dto.request.TableRequestDto;
import com.reservly.restaurants.model.dto.request.CreateRestaurantRequestDto;
import com.reservly.restaurants.model.dto.request.UpdateRestaurantRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RestaurantService {

    RestaurantDto createRestaurant(CreateRestaurantRequestDto request);

    RestaurantDto updateRestaurant(Long restaurantId, UpdateRestaurantRequestDto request);

    RestaurantDto getRestaurantDetails(Long id);

    Page<RestaurantDto> listRestaurants(Pageable pageable);

    TableDto createTable(Long restaurantId, TableRequestDto request);

    TableDto updateTable(Long tableId, TableRequestDto request);
}
