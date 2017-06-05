package com.ryanair.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ryanair.model.Flight;
import com.ryanair.services.FlightService;



/*
La aplicación puede consumir datos de los siguientes dos microservicios: - Rutas API:
Https://api.ryanair.com/core/3/routes/ que devuelve una lista de todas las rutas disponibles basadas en el Códigos IATA del aeropuerto
[
	{
		"airportFrom": "LUZ", # a departure airport IATA code
		"airportTo": "STN", # an arrival airport IATA code
		"newRoute": false,
		"seasonalRoute": false
	},
	{
		"airportFrom": "CHQ",
		"airportTo": "SKG",
		"newRoute": false,
		"seasonalRoute": false
	},
	(...)
]


https://api.ryanair.com/timetable/3/schedules/{departure}/{arrival}/years/{year}/months/{month}
Que devuelve una lista de vuelos disponibles para un determinado aeropuerto de salida código IATA, un aeropuerto de llegada
Código IATA, un año y un mes. Por ejemplo
(https://api.ryanair.com/timetable/3/schedules/DUB/WRO/years/2016/months/6)
{
	"month": 6, # a month of a year
	"days": [
				{
				"day": 1, # a day of a month
				"flights": [ # a list of flights for given day
								{
									"number": "1926", # a flight number
									"departureTime": "17:50", # a departure time in the departure
									airport timezone
									"arrivalTime": "21:25" # an arrival time in the arrival airport
									timezone
								}
							]
				},
				{
				"day": 2,
				"flights": [
								{
									"number": "1926",
									"departureTime": "19:30",
									"arrivalTime": "23:05"
								}
							]
				},
			]
}
Requirements:
La aplicación debe responder al siguiente URI de petición con parámetros de consulta dados:
http://<HOST>/<CONTEXT>/interconnections?departure={departure}&arrival={arrival}&departureDateTime={departureDateTime}&
                                         arrivalDateTime={arrivalDateTime}
departure 			- departure airport IATA code
departureDateTime 	- departure datetime in the departure airport timezone in ISO format
arrival 			- arrival airport IATA code
arrivalDateTime 	- arrival datetime in the arrival airport timezone in ISO format
*/


//http://<HOST>/<CONTEXT>/interconnections?
	//departure={departure}&
	//arrival={arrival}&
	//departureDateTime={departureDateTime}&
  //arrivalDateTime={arrivalDateTime}

@Controller
@RequestMapping("/interconnections")
public class JSONController {
	
	@Autowired
	private FlightService flightService;

	public @ResponseBody
	ResponseEntity<List<Flight>> giveMeFlights(@RequestParam("departure") String departure,
			@RequestParam("departureDateTime") String departureDateTime,
			@RequestParam("arrival") String arrival,
			@RequestParam("arrivalDateTime") String arrivalDateTime)  {
		
		List<Flight> flights = flightService.getFlights(departure, arrival, departureDateTime, arrivalDateTime);
		return new ResponseEntity<List<Flight>>(flights, HttpStatus.OK);

	}

}