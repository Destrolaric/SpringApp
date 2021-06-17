package ru.itmo.tripService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trip { // это не все офк, просто затестить

    private Double latitude;
    private Double longitude;
}
