package com.emrep.flights.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class FlightCriteria {
    private String departureAirport;
    private String arrivalAirport;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate departureDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate arrivalDate;

    private Integer pageSize;
    private Integer pageNo = 1;

    private EnumSort sort;
    private EnumOrder order = EnumOrder.ASC;

}
