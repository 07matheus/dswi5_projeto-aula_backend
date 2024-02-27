package com.projeto.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.models.data.LoginRequest;
import com.projeto.models.data.LoginResponse;
import com.projeto.models.service.LoginService;
import com.projeto.web.response.MensagemSistema;
import com.projeto.web.swagger.LoginRestControllerApi;

@RestController
@RequestMapping(value = "/rest", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginRestController implements LoginRestControllerApi {
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private MensagemSistema<LoginResponse> mensagem;
	
	@Override
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(
			@RequestBody LoginRequest login
	) {
		var loginResponse = loginService.login(login);
		
		mensagem.showMensagem(null, HttpStatus.OK.value(), loginResponse);
		
		return ResponseEntity.status(HttpStatus.OK.value()).body(mensagem);
	}

	@Override
	public ResponseEntity<?> refreshToken(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> logout(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> validarToken(String accessToken, String refreshToken) {
		// TODO Auto-generated method stub
		return null;
	}

}
