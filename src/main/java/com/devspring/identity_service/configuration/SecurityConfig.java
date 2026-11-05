package com.devspring.identity_service.configuration;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;

import lombok.experimental.NonFinal;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // cach 2: phan quyen tren method
public class SecurityConfig {

	private final String[] AUTH_WHITELIST = {
			"/users",
			"/auth/token",
			"/auth/introspect"
	};

	@NonFinal
	@Value("${app.security.jwt.secretKey}")
	private String secretKey;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(HttpMethod.POST, AUTH_WHITELIST).permitAll()
				// .requestMatchers(HttpMethod.GET, "/users").hasRole(Role.ADMIN.name())
				// cach 1: phan quyen tren enpoint JWT

				.anyRequest()
				.authenticated());

		httpSecurity.oauth2ResourceServer(oauth2 -> oauth2.jwt(
				jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())
						.jwtAuthenticationConverter(jwtAuthenticationConverter()))
				.authenticationEntryPoint(new JwtAuthenticationEntryPoint()));

		httpSecurity.csrf(csrf -> csrf.disable());

		return httpSecurity.build();
	}

	JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

		return jwtAuthenticationConverter;
	}

	JwtDecoder jwtDecoder() {
		SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HS512");

		return NimbusJwtDecoder
				.withSecretKey(secretKeySpec)
				.macAlgorithm(MacAlgorithm.HS512)
				.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
}