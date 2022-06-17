package it.epicode.energia.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModificaStatoFatturaDTO {
	
	@NotNull
	private int id;
	@NotNull
	private String stato;

}
