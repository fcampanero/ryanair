package com.ryanair.services;

import com.ryanair.model.Flight;
import com.ryanair.model.Respuesta;

public interface RyanairRequestService {

	Respuesta peticion(Flight flight, Class<?> view);

}