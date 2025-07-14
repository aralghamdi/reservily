package com.reservly.restaurants.controller;

import com.reservly.restaurants.model.dto.CityDto;
import com.reservly.restaurants.model.dto.CuisineDto;
import com.reservly.restaurants.model.dto.DistrictDto;
import com.reservly.restaurants.service.LookupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lookup")
@RequiredArgsConstructor
public class LookupController {
    private final LookupService lookupService;

    @GetMapping("/cities")
    ResponseEntity<List<CityDto>> listCities(){
        return ResponseEntity.ok(lookupService.listCities());
    }


    @GetMapping("/districts")
    ResponseEntity<List<DistrictDto>> listDistricts(){
        return ResponseEntity.ok(lookupService.listDistricts());
    }


    @GetMapping("/cuisines")
    ResponseEntity<List<CuisineDto>> listCuisines(){
        return ResponseEntity.ok(lookupService.listCuisines());
    }
}
