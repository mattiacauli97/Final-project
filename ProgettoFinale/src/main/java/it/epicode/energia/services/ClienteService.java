package it.epicode.energia.services;

/**
 * classe service dei clienti
 */
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicode.energia.dto.InserisciClienteDTO;
import it.epicode.energia.dto.ModificaClienteDTO;
import it.epicode.energia.errors.ElementoNonTrovatoException;
import it.epicode.energia.model.Cliente;
import it.epicode.energia.model.Indirizzo;
import it.epicode.energia.model.TipoIndirizzo;
import it.epicode.energia.repository.ClienteRepository;
import it.epicode.energia.repository.ComuneRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@Service
public class ClienteService {
	
	private ClienteRepository cr;
	private ComuneRepository comr;
	
	public void inserisci(InserisciClienteDTO dto) {
		Cliente c = new Cliente();
		BeanUtils.copyProperties(dto, c);
		if(dto.getIndirizzoLegale() != null) {
			Indirizzo indirizzoLegale = new Indirizzo();
			BeanUtils.copyProperties(dto.getIndirizzoLegale(), indirizzoLegale);
			indirizzoLegale.setTipo(TipoIndirizzo.SEDE_LEGALE);
			indirizzoLegale.setComune(comr.findById(dto.getIndirizzoLegale().getIdComune()).get());
			indirizzoLegale.setCliente(c);
			c.getIndirizzi().add(indirizzoLegale);
		}
		if(dto.getIndirizzoOperativo() != null) {
			Indirizzo indirizzoOperativo = new Indirizzo();
			BeanUtils.copyProperties(dto.getIndirizzoOperativo(), indirizzoOperativo);
			indirizzoOperativo.setTipo(TipoIndirizzo.SEDE_OPERATIVA);
			indirizzoOperativo.setComune(comr.findById(dto.getIndirizzoOperativo().getIdComune()).get());
			indirizzoOperativo.setCliente(c);
			c.getIndirizzi().add(indirizzoOperativo);
		}
		cr.save(c);
	}
	
	public void modifica(ModificaClienteDTO dto) throws ElementoNonTrovatoException {
		if(!cr.existsById(dto.getId())) {
			throw new ElementoNonTrovatoException("nessun elemento con quell'id presente nel db");
		}
		Cliente c = new Cliente();
		BeanUtils.copyProperties(dto, c);
		cr.save(c);
	}
	
	public void elimina(long id) throws ElementoNonTrovatoException {
		if(!cr.existsById(id)) {
			throw new ElementoNonTrovatoException("nessun elemento con quell'id presente nel db");
		}
		cr.deleteById(id);
	}
	
	public Cliente ricercaPerId(long id) throws ElementoNonTrovatoException {
		if(!cr.existsById(id)) {
			throw new ElementoNonTrovatoException("nessun elemento con quell'id presente nel db");
		}
		return cr.findById(id).get();
	}
	
	public Page getAllPaged(Pageable page) {
		return cr.findAll(page);
	}
	public List<Cliente> filterByName(String nomeContatto){
		return cr.filterByNomeContatto(nomeContatto);
	}
	public List<Cliente> filterByFatturatoAnnuale(float minFatturatoAnnuale,float maxFatturatoAnnuale){
		return cr.filterByFatturato(minFatturatoAnnuale, maxFatturatoAnnuale);
	}
	public List<Cliente> filterByDataInserimento(LocalDate dataInserimento){
		return cr.filterByDataInserimento(dataInserimento);
	}
	public List<Cliente> filterByDataUltimoContatto(LocalDate dataUltimoContatto){
		return cr.filterByDataUltimoContatto(dataUltimoContatto);
	}

}
