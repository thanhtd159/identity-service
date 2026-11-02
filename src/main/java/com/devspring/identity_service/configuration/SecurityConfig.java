package com.devspring.identity_service.configuration;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer.JwtConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;

import lombok.experimental.NonFinal;

@Configuration
@EnableWebSecurity
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
		// httpSecurity
		// .csrf(Customizer.withDefaults())
		// .httpBasic(Customizer.withDefaults())
		// .formLogin(Customizer.withDefaults())
		// .authorizeHttpRequests(authorize -> authorize
		// .anyRequest().authenticated());

		httpSecurity.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(HttpMethod.POST, AUTH_WHITELIST).permitAll()
				.anyRequest()
				.authenticated());

		httpSecurity.oauth2ResourceServer(oauth2 -> oauth2.jwt(
				jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())));

		httpSecurity.csrf(csrf -> csrf.disable());
		// httpSecurity.formLogin(form -> form.disable());
		// httpSecurity.httpBasic(basic -> basic.disable());

		return httpSecurity.build();
	}

	JwtDecoder jwtDecoder() {
		SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA512");

		return NimbusJwtDecoder
				.withSecretKey(secretKeySpec)
				.macAlgorithm(MacAlgorithm.HS512)
				.build();
	}
}