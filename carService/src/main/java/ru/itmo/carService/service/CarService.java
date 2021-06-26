package ru.itmo.carService.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.stereotype.Service;
import ru.itmo.carService.model.Car;
import ru.itmo.carService.repository.CrudCarRepository;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class CarService {

    private final CrudCarRepository repository;

    public void save(Car car) {
        repository.save(car);
    }

    public Car getById(int id) {
        return repository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

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
