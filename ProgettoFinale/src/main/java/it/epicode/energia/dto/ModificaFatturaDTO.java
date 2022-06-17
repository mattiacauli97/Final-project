package it.epicode.energia.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModificaFatturaDTO {
	
	@NotNull
	private long numero;
	@NotNull
	private int anno;
	private LocalDate data;
	@NotNull
	private float importo;
	@NotNull
	private long id_cliente;
	@NotNull
	private int idStato;
}
