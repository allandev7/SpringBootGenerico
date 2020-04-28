package com.allan.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.allan.cursomc.domain.Cliente;
import com.allan.cursomc.repositories.ClienteRepository;
import com.allan.cursomc.services.exception.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository repo;

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private EmailService emailService;

	private Random ran = new Random();

	public void sendNewPassword(String email) {
		Cliente cliente = repo.findByEmail(email);
		if (cliente == null) {
			throw new ObjectNotFoundException(email);
		}

		String newPass = newPassword();
		cliente.setSenha(newPass);

		repo.save(cliente);

		emailService.sendNewPasswordEmail(cliente, newPass);
	}

	private String newPassword() {
		char vet[] = new char[10];
		for (int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = ran.nextInt(3);
		switch (opt) {
		case 0:
			return (char) (ran.nextInt(10) + 48);
		case 1:
			return (char) (ran.nextInt(26) + 65);
		case 2:
			return (char) (ran.nextInt(26) + 97);
		default:
			return (char) (ran.nextInt(26) + 97);
		}
	}
}
