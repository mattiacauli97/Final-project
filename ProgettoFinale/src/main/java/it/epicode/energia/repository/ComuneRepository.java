package it.epicode.energia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.epicode.energia.model.Comune;

/**
 * classe repository per i comuni
 */
public interface ComuneRepository extends JpaRepository<Comune, Integer> {

}
