package com.ryanair.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ryanair.model.Errores;
import com.ryanair.model.Flight;
import com.ryanair.model.Respuesta;

@Service
public class FlightServiceImpl implements FlightService  {
	
	@Autowired
	private RyanairService ryanerService;

	@Autowired
	private  RyanairParserService ryanairParserService;

	@Override
	public List<Flight> getFlights(String departure, String arrival, String departureDateTime, String arrivalDateTime) {
		
		Respuesta respuesta = ryanerService.listarFlights(departure, arrival, departureDateTime, arrivalDateTime);
		try {
			if (respuesta.isOk()) {
				List<Flight> listFlight = ryanairParserService.getFlights(respuesta);
				return listFlight;
			}
		} catch (Errores errores) {
			throw new Errores("Failed to get flights", errores.getError() + ": " + errores.getMessage());
		}		
		throw new Errores("Failed to get flights", respuesta.getError());
	}
}
