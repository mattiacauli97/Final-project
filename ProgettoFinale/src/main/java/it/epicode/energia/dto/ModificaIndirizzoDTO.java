package it.epicode.energia.dto;

import javax.validation.constraints.NotNull;

import it.epicode.energia.model.TipoIndirizzo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModificaIndirizzoDTO {
	
	@NotNull
	private int id;
	@NotNull
	private String via;
	@NotNull
	private int civico;
	private String localita;
	@NotNull
	private int idComune;
	@NotNull
	private long idCliente;

}
