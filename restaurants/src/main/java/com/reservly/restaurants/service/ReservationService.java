package com.reservly.restaurants.service;

import com.reservly.restaurants.model.dto.ReservationDto;
import com.reservly.restaurants.model.dto.TableDto;
import com.reservly.restaurants.model.dto.request.CreateReservationRequestDto;
import com.reservly.restaurants.model.dto.request.ListAvailableTablesRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservationService {

    Page<TableDto> listAvailableTables(ListAvailableTablesRequestDto request, Pageable pageable);

    ReservationDto bookTable(CreateReservationRequestDto request);

    Page<ReservationDto> listUserReservations(Pageable pageable);

    Page<ReservationDto> listRestaurantReservations(Long restaurantId, Pageable pageable);
}
