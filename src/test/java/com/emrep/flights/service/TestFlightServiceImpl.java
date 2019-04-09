package com.emrep.flights.service;

import com.emrep.flights.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestFlightServiceImpl {

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private ResponseEntity<List<CheapFlight>> cheapFlightResponse;
    @Mock
    private ResponseEntity<List<BusinessFlight>> businessFlightResponse;

    @BeforeEach
    void setUp() {
        List<CheapFlight> cheapFlights = new LinkedList<>();
        Date departureTime1 = new GregorianCalendar(2019, Calendar.APRIL, 5).getTime();
        CheapFlight cheapFlight = new CheapFlight("1", "ABC", "XYZ", departureTime1, Calendar.getInstance().getTime());
        cheapFlights.add(cheapFlight);
        cheapFlight = new CheapFlight("2", "AAA", "BBB", Calendar.getInstance().getTime(), Calendar.getInstance().getTime());
        cheapFlights.add(cheapFlight);

        when(restTemplate.exchange(
                ArgumentMatchers.matches(EnumProvider.CHEAP_FLIGHTS.getUrl()),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<ParameterizedTypeReference<List<CheapFlight>>>any()))
                .thenReturn(cheapFlightResponse);
        when(cheapFlightResponse.getBody()).thenReturn(cheapFlights);


        List<BusinessFlight> businessFlights = new LinkedList<>();
        LocalDateTime departureTime2 = LocalDateTime.of(2019, 4, 7, 7, 20);
        LocalDateTime arrivalTime2 = LocalDateTime.of(2019, 4, 7, 9, 45);
        BusinessFlight businessFlight = new BusinessFlight("3", "FFFF -> XYZ", departureTime2, arrivalTime2);
        businessFlights.add(businessFlight);
        businessFlight = new BusinessFlight("4", "FFFZ -> AAA", LocalDateTime.now(), LocalDateTime.now());
        businessFlights.add(businessFlight);
        businessFlight = new BusinessFlight("5", "AAA -> CCC", LocalDateTime.now(), LocalDateTime.now());
        businessFlights.add(businessFlight);

        doReturn(businessFlightResponse).when(restTemplate).exchange(
                ArgumentMatchers.matches(EnumProvider.BUSINESS_FLIGHTS.getUrl()),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<ParameterizedTypeReference<List<BusinessFlight>>>any());
        when(businessFlightResponse.getBody()).thenReturn(businessFlights);

    }

    @Test
    @DisplayName("aggregate results from two providers")
    void aggregateFlights() {
        FlightService flightService = new FlightServiceImpl(restTemplate);
        assertEquals(5, flightService.searchFlights(new FlightCriteria()).size());
    }

    @Test
    @DisplayName("find flights taking off from AAA Airport")
    void findFlightsByDepatureAirport() {
        FlightService flightService = new FlightServiceImpl(restTemplate);
        FlightCriteria flightCriteria = new FlightCriteria();
        flightCriteria.setDepartureAirport("AAA");
        assertEquals(2, flightService.searchFlights(flightCriteria).size());
    }

    @Test
    @DisplayName("find flights landing in XYZ Airport")
    void findFlightsByArrivalAirport() {
        FlightService flightService = new FlightServiceImpl(restTemplate);
        FlightCriteria flightCriteria = new FlightCriteria();
        flightCriteria.setArrivalAirport("XYZ");
        assertEquals(2, flightService.searchFlights(flightCriteria).size());
    }

    @Test
    @DisplayName("find flights taking off on 4.5.2019")
    void findFlightsByDepatureDate() {
        FlightService flightService = new FlightServiceImpl(restTemplate);
        FlightCriteria flightCriteria = new FlightCriteria();
        flightCriteria.setDepartureDate(LocalDate.of(2019, 4, 5));
        assertEquals(1, flightService.searchFlights(flightCriteria).size());
    }

    @Test
    @DisplayName("find flights landing on 4.7.2019")
    void findFlightsByArrivalDate() {
        FlightService flightService = new FlightServiceImpl(restTemplate);
        FlightCriteria flightCriteria = new FlightCriteria();
        flightCriteria.setDepartureDate(LocalDate.of(2019, 4, 7));
        assertEquals(1, flightService.searchFlights(flightCriteria).size());
    }

    @Test
    @DisplayName("find sorted flights on the second page")
    void findSortedFlightsOnSecondPage() {
        FlightService flightService = new FlightServiceImpl(restTemplate);
        FlightCriteria flightCriteria = new FlightCriteria();
        flightCriteria.setPageSize(2);
        flightCriteria.setPageNo(2);
        flightCriteria.setSort(EnumSort.DEPARTURE_AIRPORT);
        List<Flight> flights = flightService.searchFlights(flightCriteria);
        assertEquals(2, flights.size());
        assertEquals("1", flights.get(0).getId());
        assertEquals("3", flights.get(1).getId());
    }
}