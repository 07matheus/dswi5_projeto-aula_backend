package com.projeto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

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
	
}
