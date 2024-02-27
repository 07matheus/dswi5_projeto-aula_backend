package com.projeto.models.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.projeto.models.data.LoginRequest;
import com.projeto.models.data.LoginResponse;
import com.projeto.models.model.Usuario;
import com.projeto.models.repository.UsuarioRepository;
import com.projeto.models.service.LoginService;
import com.projeto.models.service.mapper.ConverterEntity;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ConverterEntity converter;
	
	@Override
	public LoginResponse login(LoginRequest login) {
		Optional<Usuario> usuarioCadastrado = usuarioRepository.findUsuarioByEmail(login.getEmail());

		if(!usuarioCadastrado.isPresent()) {
			throw new UsernameNotFoundException("O e-mail informado não está cadastrado");
		}
		
		Usuario usuario = usuarioCadastrado.get();
		
		if(login.getEmail().equals(usuario.getEmail()) && usuario.isAtivo() == false) {
			throw new LockedException("Usuario bloqueado no sitema");
		}
		
		if(login.getEmail().equals(usuario.getEmail()) && BCrypt.checkpw(login.getPassword(), usuario.getPassword())) {
			new UsernamePasswordAuthenticationToken(usuario, usuario.getPassword(), usuario.getAuthorities()) ;
		} else {
			throw new BadCredentialsException("A senha informada não é válida");
		}
			
		var loginResponse = converter.parseObject(usuario, LoginResponse.class);
		
		return loginResponse;
	}

	@Override
	public LoginResponse refreshToken(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void logout(String token) {
		// TODO Auto-generated method stub

	}

	@Override
	public LoginResponse validarToken(String accessToken, String refreshToken) {
		// TODO Auto-generated method stub
		return null;
	}

}
