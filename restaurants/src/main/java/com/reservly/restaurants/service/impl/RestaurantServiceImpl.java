package com.reservly.restaurants.service.impl;

import ch.qos.logback.core.util.StringUtil;
import com.reservly.restaurants.exception.BaseException;
import com.reservly.restaurants.mapper.GeneralMapper;
import com.reservly.restaurants.model.dao.*;
import com.reservly.restaurants.model.dto.RestaurantDto;
import com.reservly.restaurants.model.dto.TableDto;
import com.reservly.restaurants.model.dto.request.CreateRestaurantRequestDto;
import com.reservly.restaurants.model.dto.request.TableRequestDto;
import com.reservly.restaurants.model.dto.request.UpdateRestaurantRequestDto;
import com.reservly.restaurants.repository.*;
import com.reservly.restaurants.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Objects;

import static com.reservly.restaurants.util.CommonUtil.getUsername;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final CuisineRepository cuisineRepository;
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;
    private final RestaurantTableRepository tableRepository;
    private final GeneralMapper generalMapper;



    @Override
    public RestaurantDto createRestaurant(CreateRestaurantRequestDto request) {
        CuisineEntity cuisine = getCuisineById(request.getCuisineId());
        CityEntity city = getDetailedCityById(request.getCityId());
        DistrictEntity district = getDistrictById(request.getDistrictId());

        validateDistrictBelongToCity(district, city);
        validateOpenCloseTime(request.getOpenTime(), request.getCloseTime());

        RestaurantEntity restaurant = generalMapper.toRestaurantEntity(getUsername(), request, cuisine, city, district);
        restaurantRepository.save(restaurant);

        return generalMapper.toRestaurantDto(restaurant);
    }

    @Override
    public RestaurantDto updateRestaurant(Long restaurantId, UpdateRestaurantRequestDto request) {
        RestaurantEntity restaurant = getRestaurantById(restaurantId);


        if(StringUtil.notNullNorEmpty(request.getName())){
            restaurant.setName(restaurant.getName());
        }

        if(request.getCuisineId() != null){
            CuisineEntity cuisine = getCuisineById(request.getCuisineId());
            restaurant.setCuisine(cuisine);
        }

        if(request.getCityId() != null){
            CityEntity city = getDetailedCityById(request.getCityId());
            restaurant.setCity(city);
        }

        if(request.getDistrictId() != null){
            DistrictEntity district = getDistrictById(request.getDistrictId());
            restaurant.setDistrict(district);
        }

        if(request.getOpenTime() != null){
            restaurant.setOpenTime(request.getOpenTime());
        }

        if(request.getCloseTime() != null){
            restaurant.setCloseTime(request.getCloseTime());
        }

        validateDistrictBelongToCity(restaurant.getDistrict(), restaurant.getCity());
        validateOpenCloseTime(restaurant.getOpenTime(), restaurant.getCloseTime());

        return generalMapper.toRestaurantDto(restaurantRepository.save(restaurant));
    }

    @Override
    public RestaurantDto getRestaurantDetails(Long id) {
        return generalMapper.toRestaurantDto(getRestaurantByIdDetailed(id));
    }

    @Override
    public Page<RestaurantDto> listRestaurants(Pageable pageable) {
        Page<RestaurantEntity> restaurants = restaurantRepository.findAllDetailed(pageable);
        return restaurants.map(generalMapper::toRestaurantDto);
    }

    @Override
    public TableDto createTable(Long restaurantId, TableRequestDto request) {
        RestaurantEntity restaurant = getRestaurantById(restaurantId);

        RestaurantTableEntity table = generalMapper.toRestaurantTableEntity(request, restaurant);

        return generalMapper.toTableDto(tableRepository.save(table));
    }

    @Override
    public TableDto updateTable(Long tableId, TableRequestDto request) {
        RestaurantTableEntity table = getTableById(tableId);

        return generalMapper.toTableDto(tableRepository.save(table));
    }


    private void validateDistrictBelongToCity(DistrictEntity district, CityEntity city){
        if(!Objects.equals(district.getCity().getId(), city.getId())){
            throw new BaseException(HttpStatus.BAD_REQUEST, "District not belong to city");
        }
    }

    private void validateOpenCloseTime(LocalTime openTime, LocalTime closeTime){
        if(openTime.isAfter(closeTime)){
            throw new BaseException(HttpStatus.BAD_REQUEST, "Open time can not be after close time");
        }
    }

    private RestaurantEntity getRestaurantById(Long id){
        return restaurantRepository.findById(id).orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND, "Restaurant not found"));
    }

    private RestaurantEntity getRestaurantByIdDetailed(Long id){
        return restaurantRepository.findByIdDetailed(id).orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND, "Restaurant not found"));
    }

    private CuisineEntity getCuisineById(Long id){
        return cuisineRepository.findById(id).orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND, "Cuisine not found"));
    }

    private CityEntity getDetailedCityById(Long id) {
        return cityRepository.findById(id).orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND, "City not found"));
    }

    private DistrictEntity getDistrictById(Long id) {
        return districtRepository.findById(id).orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND, "District not found"));
    }

    private RestaurantTableEntity getTableById(Long id) {
        return tableRepository.findById(id).orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND, "Table not found"));
    }
}
