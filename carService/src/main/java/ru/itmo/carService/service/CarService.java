package ru.itmo.carService.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.carService.model.Car;
import ru.itmo.carService.model.CarStatus;
import ru.itmo.carService.repository.CrudCarRepository;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class CarService {

    private final CrudCarRepository repository;

    public Car save(Car car) {
        return repository.save(car);
    }

    public Car getById(long id) {
        return repository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public Car findNearestCar(Double latitude,
                              Double longitude) {

        List<Car> vacantCars = repository.findAllByStatus(CarStatus.VACANT);
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
