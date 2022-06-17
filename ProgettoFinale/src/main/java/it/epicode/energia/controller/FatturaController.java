package it.epicode.energia.controller;

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
import it.epicode.energia.dto.InserisciFatturaDTO;
import it.epicode.energia.dto.ModificaFatturaDTO;
import it.epicode.energia.errors.ElementoNonTrovatoException;
import it.epicode.energia.services.FatturaService;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * classe controller per le fatture
 * @author mcauli
 */

@RestController
@RequestMapping("/fatture")
@Data
@AllArgsConstructor
public class FatturaController {
	
	private FatturaService fs;
	
	/**
	 * metodo per inserire una fattura
	 * @param dto
	 * @return
	 */
	@SecurityRequirement(name= "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary= "Inserisci una fattura",description="Inserisce una fattura nel db con i suoi attributi")
	@ApiResponse(responseCode = "200" ,description = "Fattura inserita con successo nel db")
	@ApiResponse(responseCode = "500" , description = "Errore nel server")
	@PostMapping("/inserisci")
	public ResponseEntity inserisci(@Valid @RequestBody InserisciFatturaDTO dto) {
		fs.inserisci(dto);
		return ResponseEntity.ok("fattura inserita con successo");
	}
	
	@SecurityRequirement(name= "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary= "Elimina una fattura",description="Elimina una fattura presente nel db")
	@ApiResponse(responseCode = "200" ,description = "Fattura eliminata con succecsso")
	@ApiResponse(responseCode = "500" , description = "Errore nel server")
	@DeleteMapping("cancella/{id}")
	public ResponseEntity elimina(@PathVariable(name = "id") int numero) {
		fs.delete(numero);
		return ResponseEntity.ok("Fattura cancellata");
	}

	/**
	 * metidi per modifiare una fattura
	 * @param dto
	 * @return
	 */
	@SecurityRequirement(name= "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary= "Modifica una fattura",description="Modifica una fattura presente nel db")
	@ApiResponse(responseCode = "200" ,description = "Fattura modificata con successo")
	@ApiResponse(responseCode = "500" , description = "Errore nel server")
	@PutMapping("/modifica")
	public ResponseEntity modifica(@Valid @RequestBody ModificaFatturaDTO dto) {
		fs.modifica(dto);
		return ResponseEntity.ok("Fattura modificata");
	}
	
	/**
	 * metodo per trovare una fattura per id
	 * @param id
	 * @return
	 * @throws ElementoNonTrovatoException
	 */
	@SecurityRequirement(name= "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@Operation(summary= "Cerca fattura per id",description="Cerca una fattura presente nel db")
	@ApiResponse(responseCode = "200" ,description = "Fattura modificata con successo")
	@ApiResponse(responseCode = "500" , description = "Errore nel server")
	@GetMapping("/trovaperid/{id}")
	public ResponseEntity getById(@PathVariable(name = "id") long id) throws ElementoNonTrovatoException {
		return ResponseEntity.ok(fs.getFatturaById(id));
	}
	
	/**
	 * metodo per trovare tutte le fatture paginate
	 * @param page
	 * @return
	 */
	@SecurityRequirement(name= "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@Operation(summary= "Visualizza fatture con paging",description="Visualizza tutte le fatture presenti nel db ")
	@ApiResponse(responseCode = "200" ,description = "Lista delle fatture stampata con successo")
	@ApiResponse(responseCode = "500" , description = "Errore nel server")
	@GetMapping("/trovatutte")
	public ResponseEntity getAllPaged(Pageable page) {
		return ResponseEntity.ok(fs.getAllPaged(page));
	}
	
	/**
	 * metodo per filtrare le fatture per cliente
	 * @param id
	 * @return
	 */
	@SecurityRequirement(name= "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@Operation(summary= "Visualizza fatture di un cliente",description="Visualizza tutte le fatture presenti nel db di un cliente data la sua partita iva")
	@ApiResponse(responseCode = "200" ,description = "Lista delle fatture stampata con successo")
	@ApiResponse(responseCode = "500" , description = "Errore nel server")
	@GetMapping("/filter-by-cliente/{id}")
	public ResponseEntity filterByCliente(@PathVariable long id ) {
		return ResponseEntity.ok(fs.filterByCliente(id));
	}
	
	/**
	 * metodo per filtrare le fatture tramite l'id delllo stato fattura
	 * @param id
	 * @return
	 */
	@SecurityRequirement(name= "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@Operation(summary= "Visualizza fatture filtrate per stato",description="Visualizza tutte le fatture presenti nel db con un dato stato")
	@ApiResponse(responseCode = "200" ,description = "Lista delle fatture stampata con successo")
	@ApiResponse(responseCode = "500" , description = "Errore nel server")
	@GetMapping("/filter-by-stato/{stato}")
	public ResponseEntity filterByStato(@PathVariable(name = "stato") int id) {
		return ResponseEntity.ok(fs.filterByStato(id));
	}
	
	/**
	 * metodo per filtrare le fatture tramite un range anno
	 * @param minAnno
	 * @param maxAnno
	 * @return
	 */
	@SecurityRequirement(name= "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@Operation(summary= "Filtra fattura per data",description="Visualizza tutte le fatture presenti nel db in un dato range di anni")
	@ApiResponse(responseCode = "200" ,description = "Lista delle fatture stampata con successo")
	@ApiResponse(responseCode = "500" , description = "Errore nel server")
	@GetMapping("/filter-by-range-anno/{minAnno}/{maxAnno}")
	public ResponseEntity filterByRangeAnno(@PathVariable int minAnno, @PathVariable int maxAnno) {
		return ResponseEntity.ok(fs.filterByRangeAnno(minAnno, maxAnno));
	}
	
	/**
	 * metodo per filtrare le fatture per data
	 * @param data
	 * @return
	 */
	@SecurityRequirement(name= "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@Operation(summary= "Visualizza fatture filtrate per data",description="Visualizza tutte le fatture presenti nel db con una data precisa")
	@ApiResponse(responseCode = "200" ,description = "Lista delle fatture stampata con successo")
	@ApiResponse(responseCode = "500" , description = "Errore nel server")
	@GetMapping("/filter-by-data/{data}")
	public ResponseEntity filterByData(@PathVariable LocalDate data) {
		return ResponseEntity.ok(fs.filterByData(data));
	}
	
	/**
	 * metodo per filtrare le fatture per anno
	 * @param anno
	 * @return
	 */
	@SecurityRequirement(name= "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@Operation(summary= "Visualizza fatture di un anno dato",description="Visualizza tutte le fatture presenti nel db di un dato anno")
	@ApiResponse(responseCode = "200" ,description = "Lista delle fatture stampata con successo")
	@ApiResponse(responseCode = "500" , description = "Errore nel server")
	@GetMapping("/filter-by-anno/{anno}")
	public ResponseEntity filterByAnno(@PathVariable int anno) {
		return ResponseEntity.ok(fs.filterByAnno(anno));
	}
	/**
	 * metodo per filtrare le fatture per importi
	 * @param minImporto
	 * @param maxImporto
	 * @return
	 */
	
	@SecurityRequirement(name= "bearerAuth")
	@PreAuthorize("isAuthenticated()")
	@Operation(summary= "Visualizza fatture comprese tra un range di importi",description="Visualizza tutte le fatture presenti nel db in un dato range di importi")
	@ApiResponse(responseCode = "200" ,description = "Lista delle fatture stampata con successo")
	@ApiResponse(responseCode = "500" , description = "Errore nel server")
	@GetMapping("/filter-by-importi/{minImporto}/{maxImporto}")
	public ResponseEntity filterByImporti(@PathVariable float minImporto,@PathVariable float maxImporto) {
		return ResponseEntity.ok(fs.filterByRangeImporti(minImporto, maxImporto));
	}

}
