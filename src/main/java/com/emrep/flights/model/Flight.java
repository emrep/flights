package com.emrep.flights.model;

import com.emrep.flights.util.DateUtil;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Flight {
    private String id;
    private String departureAirport;
    private String arrivalAirport;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private EnumProvider provider;

    public Flight(CheapFlight cheapFlight) {
        id = cheapFlight.getId();
        departureAirport = cheapFlight.getDeparture();
        arrivalAirport = cheapFlight.getArrival();
        departureTime = DateUtil.convertToLocalDateTimeViaMilisecond(cheapFlight.getDepartureTime());
        arrivalTime = DateUtil.convertToLocalDateTimeViaMilisecond(cheapFlight.getArrivalTime());
        provider = EnumProvider.CHEAP_FLIGHTS;
    }

    public Flight(BusinessFlight businessFlight) {
        id = businessFlight.getUuid();
        String []flight = businessFlight.getFlight().split(" -> ");
        departureAirport = flight[0];
        arrivalAirport = flight[1];
        departureTime = businessFlight.getDeparture();
        arrivalTime = businessFlight.getArrival();
        provider = EnumProvider.BUSINESS_FLIGHTS;
    }
}
