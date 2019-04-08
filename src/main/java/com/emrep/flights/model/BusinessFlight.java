package com.emrep.flights.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BusinessFlight {
    private String uuid;
    private String flight;
    private LocalDateTime departure;
    private LocalDateTime arrival;
}
