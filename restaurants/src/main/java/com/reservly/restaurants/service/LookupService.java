package com.reservly.restaurants.service;

import com.reservly.restaurants.model.dto.CityDto;
import com.reservly.restaurants.model.dto.CuisineDto;
import com.reservly.restaurants.model.dto.DistrictDto;

import java.util.List;

public interface LookupService {

    List<CityDto> listCities();

    List<DistrictDto> listDistricts();

    List<CuisineDto> listCuisines();
}
