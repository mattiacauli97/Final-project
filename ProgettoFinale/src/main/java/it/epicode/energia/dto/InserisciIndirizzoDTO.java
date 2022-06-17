package it.epicode.energia.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InserisciIndirizzoDTO {
	
	@NotNull
	private String via;
	@NotNull
	private int civico;
	private String localita;
	@NotNull
	private int idComune;

}
