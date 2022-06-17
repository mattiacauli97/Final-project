package it.epicode.energia.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String partitaIva;
	@Enumerated(EnumType.STRING)
	private TipoCliente ragioneSociale;
	private String email;
	private LocalDate dataInserimento;
	private LocalDate dataUltimoContatto;
	private float fatturatoAnnuale;
	private String pec;
	private String telefono;
	private String telefonoContatto;
	private String emailContatto;
	private String nomeContatto;
	private String cognomeContatto;
	@JsonIgnore
	@OneToMany(mappedBy = "cliente", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
	private List<Fattura> fatture =  new ArrayList<Fattura>();
	@JsonIgnore
	@OneToMany(mappedBy = "cliente", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
	private List<Indirizzo> indirizzi = new ArrayList<Indirizzo>();

}
