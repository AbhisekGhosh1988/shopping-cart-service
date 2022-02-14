package com.abhisek.mindtree.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.abhisek.mindtree.constant.Constants;

import java.util.Collections;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.AllArgsConstructor;

@Configuration
@OpenAPIDefinition
@AllArgsConstructor
public class OpenAPIConfig {
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().components(new Components()).info(new Info().title(Constants.OPENAPI_TITLE)
				.description(Constants.OPENAPI_DESC).contact(getContact())
				.license(getLicense()).termsOfService(Constants.OPENAPI_POLICY).version(Constants.OPENAPI_VER));
	}

	private License getLicense() {
		License license = new License();
		license.setName(Constants.OPENAPI_COPYRIGHT);
		license.setUrl(Constants.OPENAPI_COPYRIGHT_URL);
		return null;
	}

	private Contact getContact() {
		Contact contact = new Contact();
		contact.setEmail(Constants.OPENAPI_CONTACT_EMAIL);
		contact.setName(Constants.OPENAPI_CONTACT_NAME);
		contact.setUrl(Constants.OPENAPI_CONTACT_URL);
		return contact;
	}
}
