package com.ryanair.services;

import java.util.List;

import com.ryanair.model.Flight;
import com.ryanair.model.Respuesta;

public interface RyanairParserService {

	public List<Flight> getFlights(Respuesta respuesta);

}
