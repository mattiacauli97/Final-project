package it.epicode.energia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.epicode.energia.model.Provincia;

/**
 * classe repository per le province
 */
public interface ProvinciaRepository extends JpaRepository<Provincia, String>{

}
