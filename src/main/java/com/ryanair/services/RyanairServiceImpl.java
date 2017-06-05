package com.ryanair.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ryanair.model.Flight;
import com.ryanair.model.Respuesta;
import com.ryanair.views.Views;

@Service
public class RyanairServiceImpl implements RyanairService {

	@Autowired
	private RyanairRequestService ryanairRequestService;

	
	@Override
	public Respuesta listarFlights(String departure, String arrival, String departureDateTime) {
		Flight flight = new Flight();
		
		return ryanairRequestService.peticion(flight, Views.Ryanair.Listar.class);
	}

	

}
