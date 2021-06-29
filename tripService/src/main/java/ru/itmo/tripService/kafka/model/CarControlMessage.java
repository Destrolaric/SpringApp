package ru.itmo.tripService.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarControlMessage {

    private Long tripId;
    private Long carId;

    private Double latitude;
    private Double longitude;
}
