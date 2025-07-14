package com.reservly.restaurants.repository;

import com.reservly.restaurants.model.dao.RestaurantTableEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantTableRepository extends JpaRepository<RestaurantTableEntity, Long> {

    @EntityGraph(attributePaths = {"restaurant", "restaurant.district", "restaurant.city", "restaurant.cuisine"})
    Page<RestaurantTableEntity> findByRestaurantDistrictIdAndIdNotIn(Long districtId, List<Long> excludedIds, Pageable pageable);

    @EntityGraph(attributePaths = {"restaurant", "restaurant.district", "restaurant.city", "restaurant.cuisine"})
    Page<RestaurantTableEntity> findByRestaurantCuisineIdAndIdNotIn(Long cuisineId, List<Long> excludedIds, Pageable pageable);

    @EntityGraph(attributePaths = {"restaurant", "restaurant.district", "restaurant.city", "restaurant.cuisine"})
    Page<RestaurantTableEntity> findByRestaurantDistrictId(Long districtId, Pageable pageable);

    @EntityGraph(attributePaths = {"restaurant", "restaurant.district", "restaurant.city", "restaurant.cuisine"})
    Page<RestaurantTableEntity> findByRestaurantCuisineId(Long cuisineId, Pageable pageable);
}
