package com.dev.spring.security.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com")
public class JavaSpringSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaSpringSecurityApplication.class, args);
	}

}
