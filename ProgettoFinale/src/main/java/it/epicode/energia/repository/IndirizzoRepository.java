package it.epicode.energia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.epicode.energia.model.Indirizzo;

/**
 * classe repository per gli indirizzi
 */
public interface IndirizzoRepository extends JpaRepository<Indirizzo, Integer>{
	
	/**
	 * metodo che trova un indirizzo per cliente
	 * @param idCliente
	 * @return
	 */
	@Query(value = "select * from indirizzo left join cliente on indirizzo.cliente_id = cliente.id where cliente.id = ?1", nativeQuery = true)
	public List<Indirizzo> findByIdCliente(long idCliente);

}
