package it.epicode.energia.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.epicode.energia.dto.InserisciStatoFatturaDTO;
import it.epicode.energia.dto.ModificaStatoFatturaDTO;
import it.epicode.energia.errors.ElementoNonTrovatoException;
import it.epicode.energia.services.StatoFatturaService;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * classe controller per gli stati fattura
 * @author mcauli
 */

@RestController
@Data
@AllArgsConstructor
@RequestMapping("/statifatture")
public class StatoFatturaController {
	
	private StatoFatturaService sfs;
	
	/**
	 * metodo per inserire uno stato fattura
	 * @param dto
	 * @return
	 */
	@SecurityRequirement(name= "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary= "Inserisci stato fattura",description="Inserisce uno stato fattura nel db")
	@ApiResponse(responseCode = "200" ,description = "Stato fattura modificato con successo")
	@ApiResponse(responseCode = "500" , description = "Errore nel server")
	@PostMapping("/inserisci")
	public ResponseEntity insert(@Valid @RequestBody InserisciStatoFatturaDTO dto) {
		sfs.insert(dto);
		return ResponseEntity.ok("Stato fattura inserito nel db");
	}
	
	/**
	 * metodo per eliminare uno stato fattura
	 * @param id
	 * @return
	 * @throws ElementoNonTrovatoException
	 */
	@SecurityRequirement(name= "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary= "Elimina stato fattura",description="Elimina uno stato fattura presente nel db")
	@ApiResponse(responseCode = "200" ,description = "Stato fattura modificato con successo")
	@ApiResponse(responseCode = "500" , description = "Errore nel server")
	@DeleteMapping("/elimina/{id}")
	public ResponseEntity delete(@PathVariable int id) throws ElementoNonTrovatoException {
		sfs.delete(id);
		return ResponseEntity.ok("Stato fattura cancellato");
	}

	/**
	 * metidi per modificare uno stato fattura
	 * @param dto
	 * @return
	 * @throws ElementoNonTrovatoException
	 */
	@SecurityRequirement(name= "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary= "Modifica stato fattura",description="Modifica uno stato fattura presente nel db")
	@ApiResponse(responseCode = "200" ,description = "Stato fattura modificato con successo")
	@ApiResponse(responseCode = "500" , description = "Errore nel server")
	@PutMapping("/modifica")
	public ResponseEntity update(@Valid @RequestBody ModificaStatoFatturaDTO dto) throws ElementoNonTrovatoException {
		sfs.update(dto);
		return ResponseEntity.ok("Stato fattura modificato");
	}
	
	/**
	 * metodo per trovare tutti gli stati fattura
	 * @return
	 */
	@SecurityRequirement(name= "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@Operation(summary= "Visualizza tutti gli stati fattura",description="Visualizza tutti gli stati fattura presenti nel db ")
	@ApiResponse(responseCode = "200" ,description = "Lista di stati fattura stampata con successo")
	@ApiResponse(responseCode = "500" , description = "Errore nel server")
	@GetMapping("/trovatutti")
	public ResponseEntity getAllPaged() {
		return ResponseEntity.ok(sfs.getAll());
	}
	
	/**
	 * metodo per trovare uno stato fattura tramite il suo id
	 * @param id
	 * @return
	 * @throws ElementoNonTrovatoException
	 */
	@SecurityRequirement(name= "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@Operation(summary= "Visualizza uno stato fattura tramite il suo id",description="Visualizza uhno stato fattura presente nel db ")
	@ApiResponse(responseCode = "200" ,description = "Lista di indirizzi stampata con successo")
	@ApiResponse(responseCode = "500" , description = "Errore nel server")
	@GetMapping("/trovaperid/{id}")
	public ResponseEntity trovaPerId(@PathVariable int id) throws ElementoNonTrovatoException {
		return ResponseEntity.ok(sfs.getById(id));
	}

}
