package ru.itmo.tripService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.tripService.model.Trip;

@Repository
public interface CrudTripRepository extends JpaRepository<Trip, Long> {

}
