package com.emrep.flights.model;

import lombok.Data;

import java.util.Date;

@Data
public class CheapFlight {
    private String id;
    private String departure;
    private String arrival;
    private Date departureTime;
    private Date arrivalTime;

}
