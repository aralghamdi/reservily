package com.reservly.restaurants.service.impl;

import com.reservly.restaurants.exception.BaseException;
import com.reservly.restaurants.mapper.GeneralMapper;
import com.reservly.restaurants.model.dao.ReservationEntity;
import com.reservly.restaurants.model.dao.RestaurantEntity;
import com.reservly.restaurants.model.dao.RestaurantTableEntity;
import com.reservly.restaurants.model.dto.ReservationDto;
import com.reservly.restaurants.model.dto.TableDto;
import com.reservly.restaurants.model.dto.request.CreateReservationRequestDto;
import com.reservly.restaurants.model.dto.request.ListAvailableTablesRequestDto;
import com.reservly.restaurants.repository.ReservationRepository;
import com.reservly.restaurants.repository.RestaurantRepository;
import com.reservly.restaurants.repository.RestaurantTableRepository;
import com.reservly.restaurants.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.reservly.restaurants.util.CommonUtil.getUsername;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantTableRepository restaurantTableRepository;
    private final ReservationRepository reservationRepository;
    private final GeneralMapper generalMapper;


    @Override
    public Page<TableDto> listAvailableTables(ListAvailableTablesRequestDto request, Pageable pageable) {
        LocalDateTime reservationStart = request.getDateTime();
        LocalDateTime reservationEnd = reservationStart.plusMinutes(request.getDurationMinutes());


        Page<RestaurantTableEntity> availableTables;

        if(request.getDistrictId() != null){
            List<Long> reservedTables = reservationRepository.findReservedTableIdsByDistrict(request.getDistrictId(), reservationStart, reservationEnd);
            if (reservedTables.isEmpty()) {
                availableTables = restaurantTableRepository.findByRestaurantDistrictId(
                        request.getDistrictId(), pageable
                );
            } else {
                availableTables = restaurantTableRepository.findByRestaurantDistrictIdAndIdNotIn(
                        request.getDistrictId(), reservedTables, pageable
                );
            }
        } else if(request.getCuisineId() != null){
            List<Long> reservedTables = reservationRepository.findReservedTableIdsByCuisine(request.getCuisineId(), reservationStart, reservationEnd);
            if (reservedTables.isEmpty()) {
                availableTables = restaurantTableRepository.findByRestaurantCuisineId(
                        request.getCuisineId(), pageable
                );
            } else {
                availableTables = restaurantTableRepository.findByRestaurantCuisineIdAndIdNotIn(
                        request.getCuisineId(), reservedTables, pageable
                );
            }
        } else {
            throw new BaseException(HttpStatus.BAD_REQUEST, "District or Cuisine must ne provided");
        }

        return availableTables.map(generalMapper::toTableDto);
    }


    @Override
    public ReservationDto bookTable(CreateReservationRequestDto request) {
        RestaurantTableEntity tableEntity = getTableById(request.getTableId());
        RestaurantEntity restaurant = getRestaurantByIdDetailed(tableEntity.getRestaurant().getId());

        LocalDateTime reservationStart = request.getDateTime();
        LocalDateTime reservationEnd = reservationStart.plusMinutes(request.getDurationMinutes());

        validateWithinWorkingHours(restaurant, reservationStart, reservationEnd);

        boolean isReserved = reservationRepository.countReservations(request.getTableId(), reservationStart, reservationEnd) > 0;
        if(isReserved){
            throw new BaseException(HttpStatus.BAD_REQUEST, "Table is already booked within this time");
        }

        ReservationEntity reservationEntity = generalMapper.toReservationEntity(request, tableEntity, getUsername());
        reservationRepository.save(reservationEntity);

        return generalMapper.toReservationDto(reservationEntity);

    }


    @Override
    public Page<ReservationDto> listUserReservations(Pageable pageable) {
        Page<ReservationEntity> reservations = reservationRepository.findByCreatedBy(getUsername(), pageable);
        return reservations.map(generalMapper::toReservationDto);
    }


    @Override
    public Page<ReservationDto> listRestaurantReservations(Long restaurantId, Pageable pageable) {
        Page<ReservationEntity> reservations = reservationRepository.findByTableRestaurantId(restaurantId, pageable);
        return reservations.map(generalMapper::toReservationDto);
    }


    private void validateWithinWorkingHours(RestaurantEntity restaurant, LocalDateTime reservationStart, LocalDateTime reservationEnd){
        if(!restaurant.getOpenTime().isBefore(reservationStart.toLocalTime()) && !restaurant.getCloseTime().isAfter(reservationEnd.toLocalTime())) {
            throw new BaseException(HttpStatus.BAD_REQUEST, "Reservation is outside working hours");
        }
    }

    private RestaurantEntity getRestaurantById(Long id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND, "Restaurant not found"));
    }

    private RestaurantEntity getRestaurantByIdDetailed(Long id) {
        return restaurantRepository.findByIdDetailed(id).orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND, "Restaurant not found"));
    }

    private RestaurantTableEntity getTableById(Long id){
        return  restaurantTableRepository.findById(id).orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND, "Table not found"));
    }
}
