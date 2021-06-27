package ru.itmo.tripService.kafka;

import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.itmo.tripService.client.CarFeignClient;
import ru.itmo.tripService.kafka.model.CarControlMessage;
import ru.itmo.tripService.kafka.model.TripControlMessage;
import ru.itmo.tripService.model.Trip;
import ru.itmo.tripService.model.TripStatus;
import ru.itmo.tripService.service.TripService;

import java.time.LocalDateTime;


@Component
@AllArgsConstructor
public class KafkaConsumer {

    private final TripService service;

    private final KafkaTemplate<String, CarControlMessage> kafkaTemplate;

    private final CarFeignClient carClient;

    @KafkaListener(topics = "tripControl", groupId = "trips",
            containerFactory = "tripsKafkaListenerContainerFactory")
    public void receiveCarControl(TripControlMessage message) {
        System.out.println(message);

        Trip trip = service.getById(message.getTripId());

        switch (trip.getStatus()) {

            case WAITING: {
                trip.setStatus(TripStatus.TRAVELLING);
                kafkaTemplate.send("carControl",
                        new CarControlMessage(message.getTripId(), message.getCarId(),
                                trip.getFinishLat(), trip.getFinishLong()));
                trip.setStartTime(LocalDateTime.now());
                service.save(trip);
                break;
            }

            case TRAVELLING: {
                trip.setStatus(TripStatus.FINISHED);
                trip.setFinishTime(LocalDateTime.now());
                service.save(trip);
                carClient.finish(message.getCarId());
                break;
            }

            case FINISHED: {
                throw new IllegalArgumentException(
                        "Trip with id:" + message.getTripId() + " is already finished");
            }
        }

        System.out.println(trip);
    }
}
