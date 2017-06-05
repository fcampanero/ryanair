package com.ryanair.services;

import com.ryanair.model.Respuesta;

public interface RyanairService {

	public Respuesta listarFlights(String departure, String arrival, String departureDateTime, String arrivalDateTime);

}
