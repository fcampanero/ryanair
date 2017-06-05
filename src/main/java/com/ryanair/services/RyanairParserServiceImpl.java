package com.ryanair.services;

import java.io.IOException;
import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.ryanair.model.Errores;
import com.ryanair.model.Flight;
import com.ryanair.model.Respuesta;

@Service
public class RyanairParserServiceImpl implements RyanairParserService {
	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public List<Flight> getFlights(Respuesta respuesta) {
		
		String json = respuesta.getText();
		try {
			json = new JSONObject(json).getJSONArray("registros").toString();

			List<Flight> flight = mapper.readValue(json, new TypeReference<List<Flight>>() {});
			
			if (flight != null)
				return flight;
			else
				throw new Errores("Failed to get flights", "No return flights");

		} catch (IOException e) {
			throw new Errores("Failed to get flights", "Error parsing the next json: " + json + " -- " + e.getMessage());
		}
	}

	

}
