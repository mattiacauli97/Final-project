package it.epicode.energia.runner;

/**
 * classe runner per inserire i clienti le fatture gli indirizzi e gli stati fattura nel db
 */
import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import it.epicode.energia.model.Cliente;
import it.epicode.energia.model.Fattura;
import it.epicode.energia.model.Indirizzo;
import it.epicode.energia.model.StatoFattura;
import it.epicode.energia.model.TipoCliente;
import it.epicode.energia.model.TipoIndirizzo;
import it.epicode.energia.repository.ClienteRepository;
import it.epicode.energia.repository.ComuneRepository;
import it.epicode.energia.repository.FatturaRepository;
import it.epicode.energia.repository.IndirizzoRepository;
import it.epicode.energia.repository.StatoFatturaRepository;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Component
public class ClientiEFattureRunner implements CommandLineRunner{

	private ClienteRepository cr;
	private FatturaRepository fr;
	private ComuneRepository comr;
	private StatoFatturaRepository sfr;
	private IndirizzoRepository ir;
	
	@Override
	public void run(String... args) throws Exception {
		Cliente c = new Cliente();
		Indirizzo i = new Indirizzo();
		Fattura f = new Fattura();
		StatoFattura sf = new StatoFattura();
		
		c.setPartitaIva("12345");
		c.setRagioneSociale(TipoCliente.SRL);
		c.setEmail("mattiacauli@hotmail.it");
		c.setFatturatoAnnuale(150000);
		c.setPec("m.cauli5@pec.it");
		c.setTelefono("1234567890");
		c.setTelefonoContatto("1234567890");
		c.setEmailContatto("m.cauli@hotmail.it");
		c.setCognomeContatto("Cauli");
		c.setNomeContatto("Mattia");
		c.setDataInserimento(LocalDate.now());
		c.setDataUltimoContatto(LocalDate.now());
		
		i.setComune(comr.findById(1).get());
		i.setCivico(10);
		i.setCliente(c);
		i.setLocalita("boh");
		i.setTipo(TipoIndirizzo.SEDE_LEGALE);
		i.setVia("Dante Alighieri");
		
		sf.setStato("attiva");
		
		f.setAnno(2022);
		f.setCliente(c);
		f.setImporto(500);
		f.setStato(sf);
		f.setData(LocalDate.now());
		
		c.getFatture().add(f);
		c.getIndirizzi().add(i);
		sf.getFatture().add(f);
		
		cr.save(c);
	}	
	
}
