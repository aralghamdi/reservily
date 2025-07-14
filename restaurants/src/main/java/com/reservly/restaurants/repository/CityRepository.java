package com.reservly.restaurants.repository;

import com.reservly.restaurants.model.dao.CityEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Long> {

    @EntityGraph(attributePaths = {"districts"})
    @Query("SELECT c FROM CityEntity c WHERE c.id = :id")
    Optional<CityEntity> findByIdDetailed(Long id);

    @EntityGraph(attributePaths = {"districts"})
    @Query("SELECT c FROM CityEntity c")
    List<CityEntity> findAllDetailed();
}
