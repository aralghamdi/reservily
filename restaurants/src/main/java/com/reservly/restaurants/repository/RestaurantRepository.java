package com.reservly.restaurants.repository;

import com.reservly.restaurants.model.dao.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {

    @EntityGraph(attributePaths = {"cuisine", "city", "district"})
    @Query("SELECT r FROM RestaurantEntity r WHERE r.id = :id")
    Optional<RestaurantEntity> findByIdDetailed(Long id);

    @EntityGraph(attributePaths = {"cuisine", "city", "district"})
    @Query("SELECT r FROM RestaurantEntity r")
    Page<RestaurantEntity> findAllDetailed(Pageable pageable);
}
