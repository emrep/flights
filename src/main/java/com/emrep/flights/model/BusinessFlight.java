package com.emrep.flights.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessFlight {
    private String uuid;
    private String flight;
    private LocalDateTime departure;
    private LocalDateTime arrival;
}
