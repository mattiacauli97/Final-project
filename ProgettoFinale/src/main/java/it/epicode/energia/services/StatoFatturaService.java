package it.epicode.energia.services;

/**
 * classe service degli stati fattura
 */
import java.util.List;

import org.springframework.stereotype.Service;

import it.epicode.energia.dto.InserisciStatoFatturaDTO;
import it.epicode.energia.dto.ModificaStatoFatturaDTO;
import it.epicode.energia.errors.ElementoNonTrovatoException;
import it.epicode.energia.repository.StatoFatturaRepository;
import it.epicode.energia.model.StatoFattura;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Service
public class StatoFatturaService {
	
	private StatoFatturaRepository sfr;
	
	public void insert (InserisciStatoFatturaDTO dto) {
		StatoFattura statoFattura = new StatoFattura();
		statoFattura.setStato(dto.getStato());
		sfr.save(statoFattura);
	}
	
	public void delete(int id) throws ElementoNonTrovatoException {
		if(!sfr.existsById(id)) {
			throw new ElementoNonTrovatoException("Lo stato che vuoi cancellare non è presente nel db");
		}
		sfr.deleteById(id);
	}
	
	public StatoFattura update(ModificaStatoFatturaDTO dto) throws ElementoNonTrovatoException {
		if(!sfr.existsById(dto.getId())) {
			 throw new ElementoNonTrovatoException("Lo stato fattura che vuoi modificare non è presente nel db");		
		}
		StatoFattura statoFattura = sfr.findById(dto.getId()).get();
		statoFattura.setStato(dto.getStato());
		return sfr.save(statoFattura);
	}
	
	public StatoFattura getById(int id) throws ElementoNonTrovatoException{
		if(!sfr.existsById(id)) {
			throw new ElementoNonTrovatoException("Nessuno stato fattura con quell'id presente nel db");
		}
		return sfr.findById(id).get();
	}
	
	public List<StatoFattura> getAll(){
		return (List<StatoFattura>) sfr.findAll();
	}

}
