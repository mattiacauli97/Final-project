package it.epicode.energia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComuneCSV {
	
	@JsonProperty("Provincia")
	private String provincia;
	@JsonProperty("Comune")
	private String comune;
	@JsonProperty("CAP")
	private String cap;

}
