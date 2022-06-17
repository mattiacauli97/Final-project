package it.epicode.energia.impl;

import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
public Role findByRoleName( ERole nomeRuolo);
}
