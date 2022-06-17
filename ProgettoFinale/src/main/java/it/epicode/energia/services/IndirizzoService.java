package it.epicode.energia.services;

/**
 * classe service degli indirizzi
 */
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.epicode.energia.dto.InserisciSoloIndirizzoDTO;
import it.epicode.energia.dto.ModificaIndirizzoDTO;
import it.epicode.energia.errors.ElementoNonTrovatoException;
import it.epicode.energia.errors.GiaEsistenteException;
import it.epicode.energia.model.Comune;
import it.epicode.energia.model.Indirizzo;
import it.epicode.energia.repository.ClienteRepository;
import it.epicode.energia.repository.ComuneRepository;
import it.epicode.energia.repository.IndirizzoRepository;
import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class IndirizzoService {

	private ComuneRepository cr;
	private ClienteRepository clr;
	private IndirizzoRepository ir;

	public void insert(InserisciSoloIndirizzoDTO dto) throws Exception {
		if(!clr.existsById(dto.getIdCliente())) {
			throw new ElementoNonTrovatoException("id cliente non presente nel db");
		}
		List<Indirizzo> indirizziCliente = ir.findByIdCliente(dto.getIdCliente());
		if(indirizziCliente.size() > 1) {
			throw new GiaEsistenteException("il cliente ha già due indirizzi");
		}
		if(indirizziCliente.get(0).getTipo().equals(dto.getTipo())) {
			throw new GiaEsistenteException("tipo di indirizzo gia esistente");
		}
		Indirizzo i = new Indirizzo();
		Comune c = cr.findById(dto.getIdComune()).get();
		BeanUtils.copyProperties(dto, i);
		i.setCliente(clr.findById(dto.getIdCliente()).get());
		i.setComune(c);
		i.setTipo(dto.getTipo());
		ir.save(i);
	}
	public void delete(Integer id) throws ElementoNonTrovatoException {
		if(!ir.existsById(id)) {
			throw new ElementoNonTrovatoException("L'indirizzo che vuoi cancellare non è presente nel db");
		}
		ir.deleteById(id);
	}
	public void update(ModificaIndirizzoDTO dto) throws ElementoNonTrovatoException {
		if(!ir.existsById(dto.getId())) {
			throw new ElementoNonTrovatoException("L'indirizzo che vuoi modificare non è presente nel db");
		}
		Indirizzo i = ir.findById(dto.getId()).get();
		Comune c = cr.findById(dto.getIdComune()).get();
		BeanUtils.copyProperties(dto, i);
		i.setComune(c);
		ir.save(i);
	}
	public Page getAllPaged(Pageable page) {
		return ir.findAll(page);
	}
	
	public Indirizzo getById (int id) {
		return ir.findById(id).get();
	}
}
