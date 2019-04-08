package com.emrep.flights.rest;

import com.emrep.flights.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlightResource {
    private final FlightService flightService;

    @Autowired
    public FlightResource(FlightService flightService) {
        this.flightService = flightService;
    }

    @RequestMapping(value = "/flights", method = RequestMethod.GET)
    public ResponseEntity searchFlights() {
        return ResponseEntity.ok(flightService.searchFlights());
    }
}
