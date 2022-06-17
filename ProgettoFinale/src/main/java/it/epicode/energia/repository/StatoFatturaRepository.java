package it.epicode.energia.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import it.epicode.energia.model.StatoFattura;

/**
 * classe reposutory per gli stati fattura
 */
public interface StatoFatturaRepository extends PagingAndSortingRepository<StatoFattura, Integer> {

	public boolean existsByStatoAllIgnoreCase(String stato);
	public StatoFattura findByStatoAllIgnoreCase(String stato);
	
}
