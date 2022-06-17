package it.epicode.energia.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Provincia {
	
	@Id
	private String sigla;
	private String provincia;
	private String regione;
	@JsonIgnore
	@OneToMany(mappedBy = "provincia")
	List<Comune> comuni = new ArrayList<Comune>();

}
