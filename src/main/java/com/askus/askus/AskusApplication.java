package com.askus.askus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AskusApplication {

	public static void main(String[] args) {
		SpringApplication.run(AskusApplication.class, args);
	}

}
