package com.projeto.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.projeto.models.service.componentes.CriptografarSenha;
import com.projeto.models.service.security.UsuarioDetailsService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
	
	@Autowired
	private CriptografarSenha cript;
	
	@Autowired
	private UsuarioDetailsService userDetailsService; 

	@Bean
	protected SecurityFilterChain filterChainSecurity(HttpSecurity http) throws Exception {
		http.csrf(csrf->csrf.disable())
			.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // DESABIBLITA A SESSÃO DA APLICAÇÃO
			.securityMatcher("/**") /* APLICA TODAS AS VALIDAÇÕES DE SEGURANÇA EM TODAS AS REQUISIÇÕES À PARTIR DA PASTA ROOT */
			.authorizeHttpRequests(
				configurer->configurer /* MAPEAMENTO DE CONFIGURAÇÃO DE ACESSO AOS RECUROS POR MEIO DAS ROTAS */
					.requestMatchers(ConfigProjeto.WHITE_LIST_URL).permitAll()
					.requestMatchers(HttpMethod.POST, "/rest/usuario/salvar").hasRole("ADMIN")
					.requestMatchers(HttpMethod.GET, "/rest/usuario/listar").hasAnyRole("ADMIN", "USER", "GUEST")
					.anyRequest().authenticated()
			);
		
		
		return http.build();
	}
	
	@Bean
	AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		var builder = http.getSharedObject(AuthenticationManagerBuilder.class);
		builder.userDetailsService(userDetailsService)
			   .passwordEncoder(cript.passwordEncoder());
		return builder.build();
	}
	
}
