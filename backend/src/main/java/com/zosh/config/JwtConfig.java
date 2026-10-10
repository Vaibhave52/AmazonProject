package com.zosh.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class JwtConfig {

	@Value("${jwt.secret:}")
	private String secret;

	@PostConstruct
	public void init() {
		if (secret != null && !secret.isBlank()) {
			if (secret.getBytes().length < 32) {
				throw new IllegalStateException("JWT_SECRET must be at least 32 characters long");
			}
			JwtConstant.SECRET_KEY = secret;
		}
	}
}
