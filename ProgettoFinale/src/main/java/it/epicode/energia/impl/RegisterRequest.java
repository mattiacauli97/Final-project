package it.epicode.energia.impl;

import java.util.HashSet;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequest {
	private String userName;
	private String password;
	private String email;
	private ERole ruoli;
	private String nome;
	private String cognome;
}
