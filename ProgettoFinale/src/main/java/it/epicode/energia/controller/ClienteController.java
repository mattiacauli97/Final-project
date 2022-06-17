package it.epicode.energia.controller;

/**
 * classe controller per i clienti
 * @author mcauli
 */

import java.time.LocalDate;

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
import it.epicode.energia.dto.InserisciClienteDTO;
import it.epicode.energia.dto.ModificaClienteDTO;
import it.epicode.energia.errors.ElementoNonTrovatoException;
import it.epicode.energia.services.ClienteService;
import lombok.AllArgsConstructor;
import lombok.Data;

@RestController
@RequestMapping("/clienti")
@Data
@AllArgsConstructor
public class ClienteController {
	
	private ClienteService cs;
	
	/**
	 * metodo per inserire un cliente
	 * 
	 * @param dto
	 * @return
	 */
	
	@Operation(summary= "Inserisci cliente",description="Inserisce un cliente nel db con i suoi attributi")
	@ApiResponse(responseCode = "200" ,description = "Cliente inserito con successo nel db")
	@ApiResponse(responseCode = "500" , description = "Errore nel server")
	@PostMapping("/inserisci")
	@SecurityRequirement(name= "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity inserisci(@Valid @RequestBody InserisciClienteDTO dto) {
		cs.inserisci(dto);
		return ResponseEntity.ok("Cliente inserito con successo");
	}
	
	/**
	 * 
	 * metodo per modificare un cliente
	 * @param dto
	 * @return
	 * @throws ElementoNonTrovatoException
	 */
	@SecurityRequirement(name= "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary= "Modifica clente",description="Modifica un cliente presente nel db")
	@ApiResponse(responseCode = "200" ,description = "Cliente modificato con successo")
	@ApiResponse(responseCode = "500" , description = "Errore nel server")
	@PutMapping("/modifica")
	public ResponseEntity modifica(@Valid @RequestBody ModificaClienteDTO dto) throws ElementoNonTrovatoException {
		cs.modifica(dto);
		return ResponseEntity.ok("cliente modificato correttamente");
	}
	
	/**
	 * metodo per cancellare un cliente
	 * @param id
	 * @return
	 * @throws ElementoNonTrovatoException
	 */
	@SecurityRequirement(name= "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary= "Cancella cliente",description="Elimina un cliente presente nel db")
	@ApiResponse(responseCode = "200" ,description = "Cliente eliminato con successo dal db")
	@ApiResponse(responseCode = "500" , description = "Errore nel server")
	@DeleteMapping("/cancella/{id}")
	public ResponseEntity elimina(@PathVariable(name = "id") long id) throws ElementoNonTrovatoException {
		cs.elimina(id);
		return ResponseEntity.ok("cliente eliminato correttamente");
	}
	
	/**
	 * metodo per trovare un cliente per id
	 * @param id
	 * @return
	 * @throws ElementoNonTrovatoException
	 */
	@SecurityRequirement(name= "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@Operation(summary= "trova cliente per id",description="trova un cliente presente nel db tramite il suo id")
	@ApiResponse(responseCode = "200" ,description = "Cliente trovato con successo")
	@ApiResponse(responseCode = "500" , description = "Errore nel server")
	@GetMapping("/trovaperid/{id}")
	public ResponseEntity trovaPerId(@PathVariable(name = "id") long id) throws ElementoNonTrovatoException {
		return ResponseEntity.ok(cs.ricercaPerId(id));
	}
	
	/**
	 * metodo per trovare tutti i clienti paginati
	 * @param page
	 * @return
	 */
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@Operation(summary= "Visualizza clienti con paging",description="Visualizza tutti i clienti presenti nel db ")
	@ApiResponse(responseCode = "200" ,description = "Lista dei clienti stampata con successo")
	@ApiResponse(responseCode = "500" , description = "Errore nel server")
	@GetMapping("/trovatutti")
	public ResponseEntity getAllPaged(Pageable page) {
		return ResponseEntity.ok(cs.getAllPaged(page));

	}
	
	/**
	 * metodo per filtrare i clienti per nome
	 * @param nomeContatto
	 * @return
	 */
	@SecurityRequirement(name= "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@Operation(summary= "Filtra i clienti per nome",description="Filtra la lista dei clienti per il nome o per una parte di esso")
	@ApiResponse(responseCode = "200" ,description = "Lista filtrata per nome dei clienti stampata con successo")
	@ApiResponse(responseCode = "500" , description = "Errore nel server")
	@GetMapping("/filtrapernome/{nomeContatto}")
	public ResponseEntity filterByNome(@PathVariable String nomeContatto) {
		return ResponseEntity.ok(cs.filterByName(nomeContatto));
	}
	
	/**
	 * metodo per filtrare i clienti per fatturato
	 * @param minFatturatoAnnuale
	 * @param maxFatturatoAnnuale
	 * @return
	 */
	@SecurityRequirement(name= "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@Operation(summary= "Filtra clienti per fatturato",description="Filtra la lista dei clienti per il fatturato")
	@ApiResponse(responseCode = "200" ,description = "Lista filtrata per fatturato dei clienti stampata con successo")
	@ApiResponse(responseCode = "500" , description = "Errore nel server")
	@GetMapping("/filtraperfatturato/{minFatturatoAnnuale}/{maxFatturatoAnnuale}")
	public ResponseEntity filterByFatturato(@PathVariable float minFatturatoAnnuale, @PathVariable float maxFatturatoAnnuale) {
		return ResponseEntity.ok(cs.filterByFatturatoAnnuale(minFatturatoAnnuale, maxFatturatoAnnuale));
	}
	
	/**
	 * metodo per filtrare i clienti per data inserimento
	 * @param dataInserimento
	 * @return
	 */
	@SecurityRequirement(name= "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@Operation(summary= "Filtra clienti per data inserimento",description="Filtra la lista dei clienti per data di inserimento")
	@ApiResponse(responseCode = "200" ,description = "Lista filtrata per data inserimento dei clienti stampata con successo")
	@ApiResponse(responseCode = "500" , description = "Errore nel server")
	@GetMapping("/filtraperdatainserimento/{dataInserimento}")
	public ResponseEntity filterByDataInserimento(@PathVariable LocalDate dataInserimento) {
		return ResponseEntity.ok(cs.filterByDataInserimento(dataInserimento));
	}
	
	/**
	 * metodo per filtrare i clienti per data ultimo contatto
	 * @param dataUltimoContatto
	 * @return
	 */
	@SecurityRequirement(name= "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@Operation(summary= "Filtra clienti per data ultimo contatto",description="Filtra la lista dei clienti per data ultimo contatto")
	@ApiResponse(responseCode = "200" ,description = "Lista filtrata per data ultimo contatto dei clienti stampata con successo")
	@ApiResponse(responseCode = "500" , description = "Errore nel server")
	@GetMapping("/filtraperdataultimocontatto/{dataUltimoContatto}")
	public ResponseEntity filterByDataUltimoContatto(@PathVariable LocalDate dataUltimoContatto) {
		return ResponseEntity.ok(cs.filterByDataUltimoContatto(dataUltimoContatto));
	}
}
