package ru.itmo.tripService.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.itmo.tripService.model.Trip;

import java.util.Optional;

@Repository
public interface CrudTripRepository extends JpaRepository<Trip, Integer> {

    @EntityGraph(attributePaths = {"user", "car"})
    @Query("SELECT t FROM Trip t WHERE t.id=?1")
    Optional<Trip> getByIdEager(int id);
}
