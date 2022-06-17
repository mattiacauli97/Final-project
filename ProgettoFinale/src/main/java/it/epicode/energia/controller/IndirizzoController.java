package it.epicode.energia.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
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
import it.epicode.energia.dto.InserisciSoloIndirizzoDTO;
import it.epicode.energia.dto.ModificaIndirizzoDTO;
import it.epicode.energia.errors.ElementoNonTrovatoException;
import it.epicode.energia.services.IndirizzoService;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * classe controller per gli indirizzi
 * @author mcauli
 */

@RestController
@RequestMapping("/indirizzi")
@Data
@AllArgsConstructor
public class IndirizzoController {
	
	private IndirizzoService is;
	
	/**
	 * metodo per inserire un indirizzo
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@SecurityRequirement(name= "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary= "Inserisci un indirizzo",description="Inserisce una indirizzo nel db associato a un cliente")
	@ApiResponse(responseCode = "200" ,description = "Fattura inserita con successo nel db")
	@ApiResponse(responseCode = "500" , description = "Errore nel server")
	@PostMapping("/inserisci")
	public ResponseEntity inserisci(@Valid @RequestBody InserisciSoloIndirizzoDTO dto) throws Exception {
		is.insert(dto);
		return ResponseEntity.ok("indirizzo inserita con successo");
	}
	
	/**
	 * metodo per cancellare un indirizzo
	 * @param id
	 * @return
	 * @throws ElementoNonTrovatoException
	 */
	@SecurityRequirement(name= "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary= "Elimina indirizzo",description="Elimina un indirizzo presente nel db")
	@ApiResponse(responseCode = "200" ,description = "Indirizzo eliminato con successo")
	@ApiResponse(responseCode = "500" , description = "Errore nel server")
	@DeleteMapping("cancella/{id}")
	public ResponseEntity delete(@PathVariable(name = "id") int id) throws ElementoNonTrovatoException {
		is.delete(id);
		return ResponseEntity.ok("Indirizzo cancellato");
	}
	
	/**
	 * metodo per modificare un indirizzo
	 * @param dto
	 * @return
	 * @throws ElementoNonTrovatoException
	 */
	@SecurityRequirement(name= "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary= "Modifica indirizzo",description="Modifica un indirizzo presente nel db")
	@ApiResponse(responseCode = "200" ,description = "Indirizzo modificato con successo")
	@ApiResponse(responseCode = "500" , description = "Errore nel server")
	@PutMapping("/modifica")
	public ResponseEntity update(@Valid @RequestBody ModificaIndirizzoDTO dto) throws ElementoNonTrovatoException {
		is.update(dto);
		return ResponseEntity.ok("Indirizzo modificato");
	}
	
	/**
	 * metodo per trovare tutti gli indirizzi
	 * @param page
	 * @return
	 */
	@SecurityRequirement(name= "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@Operation(summary= "Visualizza tutti gli indirizzi con paging",description="Visualizza tutti gli indirizzi presenti nel db ")
	@ApiResponse(responseCode = "200" ,description = "Lista di indirizzi stampata con successo")
	@ApiResponse(responseCode = "500" , description = "Errore nel server")
	@GetMapping("trovatutti")
	public ResponseEntity getAllPaged(Pageable page) {
		return ResponseEntity.ok(is.getAllPaged(page));
	}
	
	/**
	 * metodo per trovare un indirizzo tramite il suo id
	 * @param id
	 * @return
	 */
	@SecurityRequirement(name= "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@Operation(summary= "Visualizza indirizzo per id",description="Visualizza un deterinato indirizzo presente nel db ")
	@ApiResponse(responseCode = "200" ,description = "Lista di indirizzi stampata con successo")
	@ApiResponse(responseCode = "500" , description = "Errore nel server")
	@GetMapping("/trovaperid/{id}")
	public ResponseEntity getAllPaged(@PathVariable(name = "id") int id) {
		return ResponseEntity.ok(is.getById(id));
	}
}
