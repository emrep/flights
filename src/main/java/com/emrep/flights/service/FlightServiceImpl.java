package com.emrep.flights.service;

import com.emrep.flights.model.BusinessFlight;
import com.emrep.flights.model.CheapFlight;
import com.emrep.flights.model.EnumProvider;
import com.emrep.flights.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {


    private final RestTemplate restTemplate;

    @Autowired
    public FlightServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Flight> searchFlights() {
        List<CheapFlight> cheapFlights = getCheapFlights();
        List<BusinessFlight> businessFlights = getBusinessFlights();

        List<Flight> flights = new LinkedList<>();
        cheapFlights.forEach(cheapFlight -> flights.add(new Flight(cheapFlight)));
        businessFlights.forEach(businessFlight -> flights.add(new Flight(businessFlight)));

        return flights;
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
