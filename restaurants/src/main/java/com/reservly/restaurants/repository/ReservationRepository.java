package com.reservly.restaurants.repository;

import com.reservly.restaurants.model.dao.ReservationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    @Query("SELECT r.table.id FROM ReservationEntity r WHERE r.table.restaurant.district.id = :districtId AND ((:start < r.endDateTime) AND (:end > r.dateTime))")
    List<Long> findReservedTableIdsByDistrict(@Param("districtId") Long districtId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT r.table.id FROM ReservationEntity r WHERE r.table.restaurant.cuisine.id = :cuisineId AND ((:start < r.endDateTime) AND (:end > r.dateTime))")
    List<Long> findReservedTableIdsByCuisine(@Param("cuisineId") Long cuisineId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT COUNT(r) FROM ReservationEntity r WHERE r.table.id = :tableId AND ((:start < r.endDateTime) AND (:end > r.dateTime))")
    long countReservations(@Param("tableId") Long tableId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);


    @EntityGraph(attributePaths = {"table", "table.restaurant", "table.restaurant.cuisine", "table.restaurant.city", "table.restaurant.district"})
    Page<ReservationEntity> findByCreatedBy(String username, Pageable pageable);


    Page<ReservationEntity> findByTableRestaurantId(Long restaurantId, Pageable pageable);
}
