package ru.itmo.tripService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.tripService.model.Trip;

public interface CrudTripRepository extends JpaRepository<Trip, Integer> {
}
