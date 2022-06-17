package it.epicode.energia.runner;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import it.epicode.energia.impl.ERole;
import it.epicode.energia.impl.Role;
import it.epicode.energia.impl.User;
import it.epicode.energia.impl.UserRepository;
import it.epicode.energia.runner.UserCreationRunner;
import lombok.extern.slf4j.Slf4j;

/**
 * classe runner per inserire gli utenti user e admin
 */
@Component
@Order(1)
@Slf4j
public class UserCreationRunner implements CommandLineRunner{
	
	@Autowired
	UserRepository ur;
	@Autowired
	PasswordEncoder pe;
	
	@Override
	public void run(String... args) throws Exception {
		
		Role roleAdmin = new Role();
		roleAdmin.setRoleName(ERole.ROLE_ADMIN);
		User userAdmin = new User();
		Set<Role> ruoli = new HashSet<Role>();
		ruoli.add(roleAdmin);
		userAdmin.setUsername("mcauli5");
		userAdmin.setPassword(pe.encode("cauli123"));
		userAdmin.setEmail("mattia@hotmail.it");
		userAdmin.setNome("Mattia");
		userAdmin.setCognome("Cauli");
		userAdmin.setRoles(ruoli);
		ur.save(userAdmin);
		
		Role roleUser = new Role();
		roleUser.setRoleName(ERole.ROLE_USER);
		User user = new User();
		Set<Role> ruoli1 = new HashSet<Role>();
		ruoli1.add(roleUser);
		user.setUsername("mcauli");
		user.setPassword(pe.encode("cauli123"));
		user.setEmail("mattia@gmail.it");
		user.setNome("Mattia");
		user.setCognome("Cauli");
		user.setRoles(ruoli1);
		ur.save(user);
	}	

}
