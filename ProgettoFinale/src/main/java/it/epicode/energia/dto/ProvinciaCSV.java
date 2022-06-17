package it.epicode.energia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProvinciaCSV {
	@JsonProperty("Sigla")
	private String sigla;
	@JsonProperty("Provincia")
	private String provincia;
	@JsonProperty("Regione")
	private String regione;

}
