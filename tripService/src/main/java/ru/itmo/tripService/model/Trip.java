package ru.itmo.tripService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private Long id;

    @Column(name = "user_id", nullable = false)
    @NotNull
    private Long userId;

    @Column(name = "car_id", nullable = false)
    @NotNull
    private Long carId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @NotNull
    private TripStatus status;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "finish_time", nullable = false)
    private LocalDateTime finishTime;

    @Column(name = "start_lat", nullable = false)
    @NotNull
    private Double startLat;

    @Column(name = "start_long", nullable = false)
    @NotNull
    private Double startLong;

    @Column(name = "finish_lat", nullable = false)
    @NotNull
    private Double finishLat;

    @Column(name = "finish_long", nullable = false)
    @NotNull
    private Double finishLong;
}
