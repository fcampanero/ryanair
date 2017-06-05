package com.ryanair.services;

import java.util.List;

import com.ryanair.model.Flight;

public interface FlightService {
	
	public List<Flight> getFlights(String departure, String arrival, String departureDateTime, String arrivalDateTime);

}

