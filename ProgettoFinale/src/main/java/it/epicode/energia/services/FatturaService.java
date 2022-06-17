package it.epicode.energia.services;

/**
 * clesse service delle fatture
 */
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicode.energia.dto.InserisciFatturaDTO;
import it.epicode.energia.dto.ModificaFatturaDTO;
import it.epicode.energia.errors.ElementoNonTrovatoException;
import it.epicode.energia.model.Cliente;
import it.epicode.energia.model.Fattura;
import it.epicode.energia.model.StatoFattura;
import it.epicode.energia.repository.ClienteRepository;
import it.epicode.energia.repository.FatturaRepository;
import it.epicode.energia.repository.StatoFatturaRepository;
import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class FatturaService {

	private FatturaRepository fr;
	private ClienteRepository cr;
	private StatoFatturaRepository sfr;
	
	public void inserisci (InserisciFatturaDTO dto) {
		if(!cr.existsById(dto.getId_cliente())||!sfr.existsById(dto.getIdStato())) {
			throw new EntityNotFoundException("La fattura ha bisogno di un cliente ed uno stato per essere emessa");
		}
		Fattura f = new Fattura();
		Cliente c = cr.findById(dto.getId_cliente()).get();
		StatoFattura sf = sfr.findById(dto.getIdStato()).get();
		BeanUtils.copyProperties(dto, f);
		f.setStato(sf);
		f.setCliente(c);
		fr.save(f);
	}
	
	public void modifica(ModificaFatturaDTO dto) {
		if(!fr.existsById(dto.getNumero()) ) {
			throw new EntityNotFoundException("La fattura che vuoi modificare non è presente nel db");
		}
		Fattura f = fr.findById(dto.getNumero()).get();
		Cliente c = cr.findById(dto.getId_cliente()).get();
		StatoFattura sf = sfr.findById(dto.getIdStato()).get();
		BeanUtils.copyProperties(dto, f);
		f.setStato(sf);
		f.setCliente(c);
		fr.save(f);
	}
	
	public void delete(long numero) {
		if(!fr.existsById(numero)) {
			throw new EntityNotFoundException("La fattura che vuoi eliminare non è presente nel db");
		}
		fr.deleteById(numero);
	}
	
	public Fattura getFatturaById(long id) throws ElementoNonTrovatoException { 
		if(!fr.existsById(id)) {
			throw new ElementoNonTrovatoException("Nessuna fattura con quell'id presente nel db");
		}
		return fr.findById(id).get();
	}
	
	public Page getAllPaged(Pageable page) {
		return fr.findAll(page);
	}
	public List<Fattura> filterByStato(int id){
		return fr.filterByStato(id);
	}
	public List<Fattura> filterByAnno(int anno){
		return fr.filterByAnno(anno);	
	}
	public List<Fattura> filterByData(LocalDate data){
		return fr.filterByData(data);
	}
	public List<Fattura> filterByRangeAnno(int minAnno,int maxAnno ){
		return fr.filterByRangeAnno(minAnno, maxAnno);
	}
	public List<Fattura> filterByRangeImporti(float minImporto,float maxImporto){
		return fr.filterByImporti(minImporto, maxImporto);
	}
	public List<Fattura> filterByCliente(long id){
		return fr.filterByCliente(id);	
	}
}
