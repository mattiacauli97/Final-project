package it.epicode.energia.repository;

/**
 * classe repository per il cliente
 * @author mcauli
 */

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import it.epicode.energia.model.Cliente;

public interface ClienteRepository extends PagingAndSortingRepository<Cliente, Long> {
	
	/**
	 * metodo per filtrare i clienti per nome
	 * @param nomeContatto
	 * @return
	 */
	@Query("Select c from Cliente c where c.nomeContatto like %:nomeContatto% OR lower(c.nomeContatto) like lower(concat('%',:nomeContatto,'%'))")
	public List<Cliente> filterByNomeContatto(@Param( value = "nomeContatto") String nomeContatto);
	
	/**
	 * metodo per filtrare i clienti per data inserimento
	 * @param dataInserimento
	 * @return
	 */
	@Query("Select c from Cliente c where c.dataInserimento like %:dataInserimento%")
	public List<Cliente> filterByDataInserimento(@Param(value = "dataInserimento") LocalDate dataInserimento);
	
	/**
	 * metodo per filtrare i clienti tramite data ulitmo contatto
	 * @param dataUltimoContatto
	 * @return
	 */
	@Query("Select c from Cliente c where c.dataUltimoContatto like %:dataUltimoContatto%")
	public List<Cliente> filterByDataUltimoContatto(@Param(value = "dataUltimoContatto") LocalDate dataUltimoContatto);
	
	/**
	 * metodo per filtrare i clienti per fatturato
	 * @param minFatturatoAnnuale
	 * @param maxFatturatoAnnuale
	 * @return
	 */
	@Query("Select c from Cliente c where c.fatturatoAnnuale between :minFatturatoAnnuale and :maxFatturatoAnnuale")
	public List<Cliente> filterByFatturato(@Param(value = "minFatturatoAnnuale") float minFatturatoAnnuale,@Param(value = "maxFatturatoAnnuale" ) float maxFatturatoAnnuale);
	
}
