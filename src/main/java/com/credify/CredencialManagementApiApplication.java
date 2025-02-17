package com.credify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class CredencialManagementApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(CredencialManagementApiApplication.class, args);
	}
}
