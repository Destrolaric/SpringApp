package ru.itmo.carService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.carService.model.Car;
import ru.itmo.carService.repository.CrudCarRepository;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CarService {

    @Autowired
    CrudCarRepository repository;

    public Car findNearestCar(Double latitude,
                              Double longitude) {

        List<Car> vacantCars = repository.findAllByStatus("VACANT");
        return vacantCars
                .stream()
                .min(Comparator.comparing(car -> getDistance(latitude, longitude, car)))
                .orElseThrow(NoSuchElementException::new);
    }


    private Double getDistance(Double latitude,
                               Double longitude,
                               Car    car) {

        Double latDist = Math.abs(latitude - car.getLatitude());
        Double longDist = Math.abs(longitude - car.getLongitude());
        return Math.sqrt(latDist * latDist + longDist * longDist);
    }

}
