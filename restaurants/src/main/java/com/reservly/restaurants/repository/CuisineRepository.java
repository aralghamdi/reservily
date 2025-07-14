package com.reservly.restaurants.repository;

import com.reservly.restaurants.model.dao.CuisineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuisineRepository extends JpaRepository<CuisineEntity, Long> {
}
