package com.projeto.models.service.exception;

public class ConfirmPasswordNaoInformada extends NegocioException {

	private static final long serialVersionUID = 3993894609886142094L;
	
	public ConfirmPasswordNaoInformada(String mensagem) {
		super(mensagem);
	}
	
}
