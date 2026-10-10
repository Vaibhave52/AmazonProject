package com.zosh.config;

public class JwtConstant {

	// Overridden at startup by the JWT_SECRET value (see JwtConfig and application.properties).
	// This default is for local development ONLY. Never use it in production.
	public static String SECRET_KEY = "dev-only-secret-change-me-before-deploying-0123456789";
	public static final String JWT_HEADER = "Authorization";

}
