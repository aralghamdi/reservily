package com.reservly.restaurants.controller;

import com.reservly.restaurants.model.dto.PageResponse;
import com.reservly.restaurants.model.dto.ReservationDto;
import com.reservly.restaurants.model.dto.RestaurantDto;
import com.reservly.restaurants.model.dto.TableDto;
import com.reservly.restaurants.model.dto.request.CreateRestaurantRequestDto;
import com.reservly.restaurants.model.dto.request.TableRequestDto;
import com.reservly.restaurants.model.dto.request.UpdateRestaurantRequestDto;
import com.reservly.restaurants.service.ReservationService;
import com.reservly.restaurants.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.reservly.restaurants.util.CommonUtil.buildPageResponse;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
@Validated
public class RestaurantsController {
    private final RestaurantService restaurantService;
    private final ReservationService reservationService;

    @PostMapping
    ResponseEntity<RestaurantDto> createRestaurant(@Valid @RequestBody CreateRestaurantRequestDto request) {
        return ResponseEntity.ok(restaurantService.createRestaurant(request));
    }

    @GetMapping
    ResponseEntity<PageResponse<RestaurantDto>> listRestaurants(@PageableDefault Pageable pageable) {
        PageResponse<RestaurantDto> response = buildPageResponse(restaurantService.listRestaurants(pageable));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    ResponseEntity<RestaurantDto> getRestaurantDetails(@PathVariable("id") Long id) {
        return ResponseEntity.ok(restaurantService.getRestaurantDetails(id));
    }

    @PutMapping("/{id}")
    ResponseEntity<RestaurantDto> updateRestaurant(@PathVariable("id") Long id,  @Valid @RequestBody UpdateRestaurantRequestDto request) {
        return ResponseEntity.ok(restaurantService.updateRestaurant(id, request));
    }


    @GetMapping("/{id}/reservations")
    ResponseEntity<PageResponse<ReservationDto>> listReservations(@PathVariable("id") Long id, @PageableDefault Pageable pageable) {
        PageResponse<ReservationDto> response = buildPageResponse(reservationService.listRestaurantReservations(id, pageable));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/table")
    ResponseEntity<TableDto> createTable(@PathVariable("id") Long id, @Valid @RequestBody TableRequestDto request) {
        return ResponseEntity.ok(restaurantService.createTable(id, request));
    }


    @PutMapping("/table/{id}")
    ResponseEntity<TableDto> updateTable(@PathVariable("id") Long id, @Valid @RequestBody TableRequestDto request) {
        return ResponseEntity.ok(restaurantService.updateTable(id, request));
    }
}
