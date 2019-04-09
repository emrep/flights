package com.emrep.flights.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheapFlight {
    private String id;
    private String departure;
    private String arrival;
    private Date departureTime;
    private Date arrivalTime;
}
