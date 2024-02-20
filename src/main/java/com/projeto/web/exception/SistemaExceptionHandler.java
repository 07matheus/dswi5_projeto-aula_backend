package com.projeto.web.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.projeto.models.service.exception.EmailJaCadastradoException;
import com.projeto.models.service.exception.EntityNotFoundException;
import com.projeto.models.service.exception.PasswordDiferenteConfirmePasswordException;
import com.projeto.web.response.Fields;
import com.projeto.web.response.MensagemErroSistema;


@RestControllerAdvice(basePackages = "com.projeto")
public class SistemaExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> entityNotFoundException(EntityNotFoundException ex, WebRequest request){
		MensagemErroSistema erro = new MensagemErroSistema();
		erro.setStatus(HttpStatus.NOT_FOUND.value());
		erro.setMensagem(ex.getMessage());
		erro.setData(new Date());
		erro.setError(true);
		erro.setDescricao("Registro não localizado!");
		return ResponseEntity.ok().body(erro);
	}
	
	
	@ExceptionHandler(EmailJaCadastradoException.class)
	public ResponseEntity<?> emailJaCadastradoException(EmailJaCadastradoException ex, WebRequest request){
		MensagemErroSistema erro = new MensagemErroSistema();
		erro.setStatus(HttpStatus.FOUND.value());
		erro.setMensagem(ex.getMessage());
		erro.setData(new Date());
		erro.setError(true);
		erro.setDescricao("O e-mal já está cadastrado no sistema!");
		return ResponseEntity.ok().body(erro);
	}
	
	
	@ExceptionHandler(PasswordDiferenteConfirmePasswordException.class)
	public ResponseEntity<?> passwordDiferenteConfirmePasswordException(PasswordDiferenteConfirmePasswordException ex, WebRequest request){
		MensagemErroSistema erro = new MensagemErroSistema();
		erro.setStatus(HttpStatus.FORBIDDEN.value());
		erro.setMensagem(ex.getMessage());
		erro.setData(new Date());
		erro.setError(true);
		erro.setDescricao("O campo de confirmação de senha deve ser informado!");
		return ResponseEntity.ok().body(erro);
	}
	

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
   		
		BindingResult bindingResult = ex.getBindingResult();
			
		List<Fields> fields = new ArrayList<>();
		
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			Fields f = new Fields();
			f.setNome(fieldError.getField());
			f.setMensagemCliente(message);
			fields.add(f);
		}
		
		MensagemErroSistema erro = MensagemErroSistema
				                      .builder()
					                  .addData(new Date())  
					                  .addDescricao("Informe os dados solicitados no Cadastro!!!")
					                  .addError(true)
					                  .addFields(fields)
					                  .addMensagem("Informe os dados solicitados no Cadastro!!!")
					                  .addStatus(HttpStatus.PRECONDITION_FAILED.value())
					                  .build();
		
		return ResponseEntity.ok().body(erro);
	}
	
	
}
