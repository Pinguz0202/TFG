package com.biblioteca.biblioteca_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity 
public class BibliotecaApiApplication {

	public static void main(String[] args) {
        SpringApplication.run(BibliotecaApiApplication.class, args);
    }
	
}