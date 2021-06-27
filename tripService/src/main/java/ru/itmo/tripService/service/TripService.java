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

    public Trip getById(int id) {
        return repository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public Trip getByIdEager(int id) {
        return repository.getByIdEager(id).orElseThrow(IllegalArgumentException::new);
    }

}
