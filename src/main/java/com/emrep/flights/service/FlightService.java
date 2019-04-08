package com.emrep.flights.service;

import com.emrep.flights.model.Flight;
import com.emrep.flights.model.FlightCriteria;

import java.util.List;

public interface FlightService {
    List<Flight> searchFlights(FlightCriteria flightCriteria);
}
