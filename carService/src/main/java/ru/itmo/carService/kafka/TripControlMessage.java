package ru.itmo.carService.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripControlMessage {

    private Integer tripId;
    private Integer carId;
}