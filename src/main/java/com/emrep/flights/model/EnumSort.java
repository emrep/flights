package com.emrep.flights.model;

import java.util.Comparator;

public enum EnumSort {
    DEPARTURE_AIRPORT(Comparator.comparing(Flight::getDepartureAirport)),
    ARRIVAL_AIRPORT(Comparator.comparing(Flight::getArrivalAirport)),
    DEPARTURE_TIME(Comparator.comparing(Flight::getDepartureTime)),
    ARRIVAL_TIME(Comparator.comparing(Flight::getArrivalTime));

    private Comparator<Flight> comparator;

    EnumSort(Comparator<Flight> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Flight> getComparator() {
        return comparator;
    }
}
