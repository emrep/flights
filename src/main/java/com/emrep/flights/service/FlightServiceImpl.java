package com.emrep.flights.service;

import com.emrep.flights.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl implements FlightService {


    private final RestTemplate restTemplate;

    @Autowired
    public FlightServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Flight> searchFlights(FlightCriteria flightCriteria) {
        List<CheapFlight> cheapFlights = getCheapFlights();
        List<BusinessFlight> businessFlights = getBusinessFlights();

        List<Flight> flights = new LinkedList<>();
        cheapFlights.forEach(cheapFlight -> flights.add(new Flight(cheapFlight)));
        businessFlights.forEach(businessFlight -> flights.add(new Flight(businessFlight)));

        List<Flight> filteredFlights = filterFlights(flightCriteria, flights);
        filteredFlights = sortFlights(flightCriteria, filteredFlights);
        return  paginateFlights(flightCriteria, filteredFlights);
    }

    private List<Flight> filterFlights(FlightCriteria flightCriteria, List<Flight> flights) {
        List<Flight> filteredFlights = new LinkedList<>(flights);

        if(Objects.nonNull(flightCriteria.getDepartureAirport())) {
            filteredFlights = flights.stream().filter(flight -> flight.getDepartureAirport().equals(flightCriteria.getDepartureAirport())).collect(Collectors.toList());
        }

        if(Objects.nonNull(flightCriteria.getArrivalAirport())) {
            filteredFlights = filteredFlights.stream().filter(flight -> flight.getArrivalAirport().equals(flightCriteria.getArrivalAirport())).collect(Collectors.toList());
        }

        if(Objects.nonNull(flightCriteria.getDepartureDate())) {
            filteredFlights = filteredFlights.stream().filter(flight -> flight.getDepartureTime().toLocalDate().equals(flightCriteria.getDepartureDate())).collect(Collectors.toList());
        }

        if(Objects.nonNull(flightCriteria.getArrivalDate())) {
            filteredFlights = filteredFlights.stream().filter(flight -> flight.getArrivalTime().toLocalDate().equals(flightCriteria.getArrivalDate())).collect(Collectors.toList());
        }

        return filteredFlights;
    }

    private List<Flight> sortFlights(FlightCriteria flightCriteria, List<Flight> filteredFlights) {
        if(Objects.nonNull(flightCriteria.getSort())) {
            if(flightCriteria.getOrder().equals(EnumOrder.ASC)) {
                filteredFlights = filteredFlights.stream().sorted(flightCriteria.getSort().getComparator()).collect(Collectors.toList());
            } else {
                filteredFlights = filteredFlights.stream().sorted(flightCriteria.getSort().getComparator().reversed()).collect(Collectors.toList());
            }
        }
        return filteredFlights;
    }

    private List<Flight> paginateFlights(FlightCriteria flightCriteria, List<Flight> filteredFlights) {
        if(Objects.nonNull(flightCriteria.getPageSize())) {
            Integer limit = flightCriteria.getPageSize();
            int skip = (flightCriteria.getPageNo() - 1) * limit;
            filteredFlights = filteredFlights.stream().skip(skip).limit(limit).collect(Collectors.toList());
        }
        return filteredFlights;
    }

    private List<CheapFlight> getCheapFlights() {
        ParameterizedTypeReference<List<CheapFlight>> responseType = new ParameterizedTypeReference<List<CheapFlight>>() {};
        ResponseEntity<List<CheapFlight>> response = restTemplate.exchange(EnumProvider.CHEAP_FLIGHTS.getUrl(), HttpMethod.GET, null, responseType);
        return response.getBody();
    }

    private List<BusinessFlight> getBusinessFlights() {
        ParameterizedTypeReference<List<BusinessFlight>> responseType = new ParameterizedTypeReference<List<BusinessFlight>>() {};
        ResponseEntity<List<BusinessFlight>> response = restTemplate.exchange(EnumProvider.BUSINESS_FLIGHTS.getUrl(), HttpMethod.GET, null, responseType);
        return response.getBody();
    }
}
