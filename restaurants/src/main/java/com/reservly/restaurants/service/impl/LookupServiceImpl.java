package com.reservly.restaurants.service.impl;

import com.reservly.restaurants.mapper.GeneralMapper;
import com.reservly.restaurants.model.dto.CityDto;
import com.reservly.restaurants.model.dto.CuisineDto;
import com.reservly.restaurants.model.dto.DistrictDto;
import com.reservly.restaurants.repository.CityRepository;
import com.reservly.restaurants.repository.CuisineRepository;
import com.reservly.restaurants.repository.DistrictRepository;
import com.reservly.restaurants.service.LookupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class LookupServiceImpl implements LookupService {
    private final CuisineRepository cuisineRepository;
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;
    private final GeneralMapper generalMapper;

    @Override
    public List<CityDto> listCities() {
        return cityRepository.findAllDetailed().stream().map(generalMapper::toCityDto).toList();
    }

    @Override
    public List<DistrictDto> listDistricts() {
        return districtRepository.findAll().stream().map(generalMapper::toDistrictDto).toList();
    }

    @Override
    public List<CuisineDto> listCuisines() {
        return cuisineRepository.findAll().stream().map(generalMapper::toCuisineDto).toList();
    }
}
