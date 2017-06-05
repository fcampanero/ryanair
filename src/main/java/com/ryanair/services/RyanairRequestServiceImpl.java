package com.ryanair.services;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ryanair.model.Errores;
import com.ryanair.model.Flight;
import com.ryanair.model.Respuesta;



@Service
public class RyanairRequestServiceImpl implements RyanairRequestService {

	private String url;
	private Integer timeout;	
	private ObjectMapper mapper = new ObjectMapper();
	private RestTemplate restTemplate = new RestTemplate();
	private SimpleClientHttpRequestFactory rf = new SimpleClientHttpRequestFactory();

	private String campoResult 		= "result";
	private String campoText 		= "text";
	
	public Respuesta peticion(Flight flight, Class<?> view) {
		
		Respuesta respuesta = peticion(flight, view);
		if (!respuesta.isOk()) {
			respuesta = serializarYEnviar(flight, view);
		}
		return respuesta;
	}

	private Respuesta serializarYEnviar(Object data, Class<?> view) {
		String json;
		try {
			mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
			json = mapper.writerWithView(view).writeValueAsString(data);
		} catch (IOException e) {
			throw new Errores("Error interno", "Error al intentar serializar los datos: " + e.getMessage());
		}
		
		try {
			// timeout de la conexion
			rf.setConnectTimeout(timeout * 1000);
			rf.setReadTimeout(timeout * 1000);
			restTemplate.setRequestFactory(rf);

			// Se envia el json con un POST a la url como cadena de texto. 
			ResponseEntity<String> response = restTemplate.postForEntity(url, json, String.class);			
			Respuesta respuesta = parsearRespuesta(response.getBody());
			
			return respuesta;
		} catch (Exception ex) {			
			throw new Errores(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno",
					"Error al enviar la petición: " + json);
		}
	}


	private Respuesta parsearRespuesta(String json) {
		if (json == null)
			return null;

		Respuesta respuesta = new Respuesta();
		JSONObject jsonObj = new JSONObject(json);
		try {
			respuesta.setResult(jsonObj.getString(campoResult));

			if (jsonObj.has(campoText)) {
				try {
					respuesta.setText(jsonObj.getString(campoText));
				} catch (JSONException e1) {
					try {
						respuesta.setText(jsonObj.getJSONObject(campoText).toString());
					} catch (JSONException e2) {
						respuesta.setText(jsonObj.getJSONArray(campoText).toString());
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		respuesta.setOk(isOk(respuesta));
		return respuesta;
	}

	private Boolean isOk(Respuesta respuesta) {
		if (respuesta.getResult() != null && respuesta.getResult().equals("OK"))
			return true;
		else {
			respuesta.setError(respuesta.getText());
			return false;
		}
	}

	

	

}
