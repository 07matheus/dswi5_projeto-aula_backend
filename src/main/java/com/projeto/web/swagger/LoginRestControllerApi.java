package com.projeto.web.swagger;

import org.springframework.http.ResponseEntity;

import com.projeto.models.data.LoginRequest;
import com.projeto.models.data.LoginResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Login", description = "Endpoints para o gerenciamento dos dados do usuário")
public interface LoginRestControllerApi {

	@Operation(summary = "Login de usuário ", description = "Login de usuário", responses = {
	    @ApiResponse(
	        responseCode="200",
	        content = {
	           @Content( mediaType = "application/json",array = @ArraySchema( schema=@Schema(implementation = LoginResponse.class)))		 
	    }),
	    @ApiResponse(description="Bad Request", responseCode="400", content=@Content),
	    @ApiResponse(description="Unauthorized", responseCode="401", content=@Content),
	    @ApiResponse(description="Not Found", responseCode="404", content=@Content),
	    @ApiResponse(description="Internal Error", responseCode="500", content=@Content),  
	})
	public ResponseEntity<?> login(LoginRequest login);
	
	@Operation(summary = "Renovar token", description = "Renova o token de acesso ao sistem", responses = {
		    @ApiResponse(
		        responseCode="200",
		        content = {
		           @Content( mediaType = "application/json",array = @ArraySchema( schema=@Schema(implementation = LoginResponse.class)))		 
		    }),
		    @ApiResponse(description="Bad Request", responseCode="400", content=@Content),
		    @ApiResponse(description="Unauthorized", responseCode="401", content=@Content),
		    @ApiResponse(description="Not Found", responseCode="404", content=@Content),
		    @ApiResponse(description="Internal Error", responseCode="500", content=@Content),  
		})
	public ResponseEntity<?> refreshToken(String token);
	
	@Operation(summary = "Encerrar o sitema", description = "Faz com que o usuário saia do sistema", responses = {
		    @ApiResponse(
		        responseCode="200",
		        content = {
		           @Content( mediaType = "application/json",array = @ArraySchema( schema=@Schema(implementation = LoginResponse.class)))		 
		    }),
		    @ApiResponse(description="Bad Request", responseCode="400", content=@Content),
		    @ApiResponse(description="Unauthorized", responseCode="401", content=@Content),
		    @ApiResponse(description="Not Found", responseCode="404", content=@Content),
		    @ApiResponse(description="Internal Error", responseCode="500", content=@Content),  
		})
	public ResponseEntity<?> logout(String token);
	
	@Operation(summary = "Validar token", description = "Realiza a validação do token de acesso usuario", responses = {
		    @ApiResponse(
		        responseCode="200",
		        content = {
		           @Content( mediaType = "application/json",array = @ArraySchema( schema=@Schema(implementation = LoginResponse.class)))		 
		    }),
		    @ApiResponse(description="Bad Request", responseCode="400", content=@Content),
		    @ApiResponse(description="Unauthorized", responseCode="401", content=@Content),
		    @ApiResponse(description="Not Found", responseCode="404", content=@Content),
		    @ApiResponse(description="Internal Error", responseCode="500", content=@Content),  
		})
	public ResponseEntity<?> validarToken(String accessToken, String refreshToken);
	
}
