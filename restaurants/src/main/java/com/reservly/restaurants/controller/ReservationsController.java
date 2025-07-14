package com.reservly.restaurants.controller;

import com.reservly.restaurants.model.dto.PageResponse;
import com.reservly.restaurants.model.dto.ReservationDto;
import com.reservly.restaurants.model.dto.TableDto;
import com.reservly.restaurants.model.dto.request.CreateReservationRequestDto;
import com.reservly.restaurants.model.dto.request.ListAvailableTablesRequestDto;
import com.reservly.restaurants.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.reservly.restaurants.util.CommonUtil.buildPageResponse;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
@Validated
public class ReservationsController {
    private final ReservationService reservationService;

    @PostMapping("/search")
    ResponseEntity<PageResponse<TableDto>> searchReservation(@PageableDefault Pageable pageable,  @Valid @RequestBody ListAvailableTablesRequestDto request){
        PageResponse<TableDto> response = buildPageResponse(reservationService.listAvailableTables(request, pageable));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/book")
    ResponseEntity<ReservationDto> bookTable(@Valid @RequestBody CreateReservationRequestDto request){
        return ResponseEntity.ok(reservationService.bookTable(request));
    }

    @GetMapping("/user-reservations")
    ResponseEntity<PageResponse<ReservationDto>> listUserReservations(@PageableDefault Pageable pageable){
        PageResponse<ReservationDto> response = buildPageResponse(reservationService.listUserReservations(pageable));
        return ResponseEntity.ok(response);
    }
}
