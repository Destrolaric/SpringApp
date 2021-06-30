package ru.itmo.tripService.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.tripService.model.Trip;
import ru.itmo.tripService.repository.CrudTripRepository;

@Service
@AllArgsConstructor
public class TripService {

    private final CrudTripRepository repository;

    public Trip save(Trip trip) {
        return repository.save(trip);
    }

    public Trip getById(long id) {
        return repository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

}
