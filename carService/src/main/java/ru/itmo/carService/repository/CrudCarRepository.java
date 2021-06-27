package ru.itmo.carService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.carService.model.Car;
import ru.itmo.carService.model.CarStatus;

import java.util.List;

@Repository
public interface CrudCarRepository extends JpaRepository<Car, Integer> {

    List<Car> findAllByStatus(CarStatus status);
}
