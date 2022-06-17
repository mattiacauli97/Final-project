package it.epicode.energia.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
	@Autowired UserRepository userRepository;
	@Autowired PasswordEncoder pe;
	
	
	public List<UserResponse> getAllUsersBasicInformations() {
		return userRepository.findAll()
				.stream()
				.map( user ->  UserResponse
								.builder()
								.userName(  user.getUsername()  )
								.role( user.getRoles().stream().findFirst().get().getRoleName().name() )
								.build()   
				).collect(Collectors.toList());
	}
	
	public UserResponse getUserBasicInformations(String userName) {
		User user = userRepository.findByUsername(userName).get();
		
		
		
		return UserResponse
		.builder()
		.userName(userName)
		.role( user.getRoles().stream().findFirst().get().getRoleName().name()).build();
		
	}
	public void registerUser(RegisterRequest dto) {
		Role admin = new Role();
		admin.setRoleName(ERole.ROLE_ADMIN);
		User userAdmin = new User();
		Role user = new Role();
		user.setRoleName(ERole.ROLE_USER);
		Set<Role> ruoli = new HashSet<Role>();
		ruoli.add(admin);
		ruoli.add(user);
		userAdmin.setRoles(ruoli);
		userAdmin.setUsername(dto.getUserName());
		userAdmin.setPassword((pe.encode(dto.getPassword())));
		userAdmin.setNome(dto.getNome());
		userAdmin.setCognome(dto.getCognome());
		userRepository.save(userAdmin);
		
	}

}
