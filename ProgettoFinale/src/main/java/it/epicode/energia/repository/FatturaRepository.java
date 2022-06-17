package it.epicode.energia.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import it.epicode.energia.model.Fattura;

/**
 * classe repository per le fatture
 */
public interface FatturaRepository extends PagingAndSortingRepository<Fattura, Long> {
	
	/**
	 * metodo che filtra le fatture per cliente
	 * @param id
	 * @return
	 */
	@Query(value = "select * from fattura left join cliente on fattura.cliente_id = cliente.id where cliente.id = ?1", nativeQuery = true)
	public List<Fattura> filterByCliente(long id);
	
	/**
	 * metodo che filtra le fatture per stato
	 * @param id
	 * @return
	 */
	@Query(value = "select * from fattura left join stato_fattura on fattura.stato_id = stato_fattura.id where stato_fattura.id = ?1", nativeQuery = true)
	public List<Fattura> filterByStato(int id);
	
	/**
	 * metodo che filtra le fatture tramite un range anno
	 * @param minAnno
	 * @param maxAnno
	 * @return
	 */
	@Query("Select f from Fattura f where f.anno between :minAnno and :maxAnno")
	public List<Fattura> filterByRangeAnno(@Param(value = "minAnno")int minAnno,@Param(value = "maxAnno")int maxAnno);
	
	/**
	 * metodo che filtra le fatture per data
	 * @param data
	 * @return
	 */
	@Query("Select f from Fattura f where f.data like :data")
	public List<Fattura> filterByData(@Param(value = "data")LocalDate data);
	
	/**
	 * metodo che filtra le fatture per anno
	 * @param anno
	 * @return
	 */
	@Query("Select f from Fattura f where f.anno = :anno")
	public List<Fattura> filterByAnno(@Param(value = "anno")int anno);
	
	/**
	 * metodo che filtra le fatture per importo
	 * @param minImporto
	 * @param maxImporto
	 * @return
	 */
	@Query("Select f from Fattura f where f.importo between :minImporto and :maxImporto")
	public List<Fattura> filterByImporti(@Param(value = "minImporto")float minImporto,@Param(value = "maxImporto") float maxImporto);

}
