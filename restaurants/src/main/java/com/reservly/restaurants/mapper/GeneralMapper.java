package com.reservly.restaurants.mapper;

import com.reservly.restaurants.model.dao.*;
import com.reservly.restaurants.model.dto.*;
import com.reservly.restaurants.model.dto.request.CreateReservationRequestDto;
import com.reservly.restaurants.model.dto.request.CreateRestaurantRequestDto;
import com.reservly.restaurants.model.dto.request.TableRequestDto;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames.ACTIVE;

@Mapper(componentModel = "spring")
public interface GeneralMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "seatCount", source = "seatCount")
    TableDto toTableDto(RestaurantTableEntity tableEntity);

    @AfterMapping
    default void postToTableDto(@MappingTarget TableDto tableDto, RestaurantTableEntity tableEntity){
        tableDto.setRestaurant(toRestaurantDto(tableEntity.getRestaurant()));
    }


    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "openTime", source = "openTime")
    @Mapping(target = "closeTime", source = "closeTime")
    RestaurantDto toRestaurantDto(RestaurantEntity restaurantEntity);

//    @AfterMapping
//    default void postToRestaurantDto(@MappingTarget RestaurantDto restaurantDto, RestaurantEntity restaurantEntity){
//        restaurantDto.setCity(toCityDto(restaurantEntity.getCity()));
//        restaurantDto.setDistrict(toDistrictDto(restaurantEntity.getDistrict()));
//        restaurantDto.setCuisine(toCuisineDto(restaurantEntity.getCuisine()));
//        restaurantDto.setTables(restaurantEntity.getTables().stream().map(it -> TableDto.builder()
//                .id(it.getId())
//                .seatCount(it.getSeatCount())
//                .build()).toList());
//    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "referenceNo", expression = "java(generateRefNo())")
    @Mapping(target = "table", source = "table")
    @Mapping(target = "dateTime", source = "request.dateTime")
    @Mapping(target = "endDateTime", source = "request", qualifiedByName = "getReservationEndTime")
    @Mapping(target = "durationMinutes", source = "request.durationMinutes")
    @Mapping(target = "createdBy", source = "username")
    @Mapping(target = "status", constant = "ACTIVE")
    ReservationEntity toReservationEntity(CreateReservationRequestDto request, RestaurantTableEntity table, String username);


    @Mapping(target = "referenceNo", source = "referenceNo")
    @Mapping(target = "startTime", source = "dateTime")
    @Mapping(target = "endTime", source = "endDateTime")
    @Mapping(target = "status", source = "status")
    ReservationDto toReservationDto(ReservationEntity reservationEntity);

    @AfterMapping
    default void postToReservationDto(@MappingTarget ReservationDto reservationDto, RestaurantTableEntity tableEntity){
        reservationDto.setRestaurant(toRestaurantDto(tableEntity.getRestaurant()));
    }


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "cuisine", source = "cuisine")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "district", source = "district")
    @Mapping(target = "openTime", source = "request.openTime")
    @Mapping(target = "closeTime", source = "request.closeTime")
    @Mapping(target = "createdBy", source = "username")
    RestaurantEntity toRestaurantEntity(String username, CreateRestaurantRequestDto request, CuisineEntity cuisine, CityEntity city, DistrictEntity district);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "seatCount", source = "request.seatCount")
    @Mapping(target = "restaurant", source = "restaurant")
    RestaurantTableEntity toRestaurantTableEntity(TableRequestDto request, RestaurantEntity restaurant);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CityDto toCityDto(CityEntity city);

    @AfterMapping
    default void postToCityDto(@MappingTarget CityDto cityDto, CityEntity city){
        cityDto.setDistricts(city.getDistricts().stream().map(this::toDistrictDto).toList());
    }

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "city", source = "city.name")
    DistrictDto toDistrictDto(DistrictEntity district);


    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CuisineDto toCuisineDto(CuisineEntity cuisine);

    @Named("getReservationEndTime")
    default LocalDateTime getReservationEndTime(CreateReservationRequestDto reservationRequestDto){
        LocalDateTime reservationStart = reservationRequestDto.getDateTime();
        return reservationStart.plusMinutes(reservationRequestDto.getDurationMinutes());
    }

    default String generateRefNo(){
        return UUID.randomUUID().toString().replace("-", "");
    }

}
