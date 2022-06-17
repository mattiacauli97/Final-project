package it.epicode.energia.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import it.epicode.energia.model.TipoCliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModificaClienteDTO {
	
	@NotNull
	private long id;
	@NotNull
	private String partitaIva;
	@NotNull
	private TipoCliente ragioneSociale;
	@NotNull
	private String email;
	private LocalDate dataInserimento;
	private LocalDate dataUltimoContatto;
	@NotNull
	private float fatturatoAnnuale;
	@NotNull
	private String pec;
	@NotNull
	private String telefono;
	private String telefonoContatto;
	private String emailContatto;
	@NotNull
	private String nomeContatto;
	@NotNull
	private String cognomeContatto;

}
