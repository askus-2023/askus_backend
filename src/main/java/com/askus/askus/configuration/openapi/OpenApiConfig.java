package com.askus.askus.configuration.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * OpenAPI configuration swagger-ui
 *
 * @Bean OpenAPI
 */
@Configuration(proxyBeanMethods = false)
public class OpenApiConfig {
	@Bean
	public OpenAPI openApi() {
		Info info = new Info()
			.title("COOKLE API")
			.version("version 1.0")
			.description("cookle REST API documentation");

		SecurityScheme securityScheme = new SecurityScheme()
			.type(SecurityScheme.Type.HTTP)
			.scheme("bearer")
			.bearerFormat("JWT");
		Components components = new Components().addSecuritySchemes("bearer-key", securityScheme);

		return new OpenAPI()
			.info(info)
			.components(components);
	}
}
