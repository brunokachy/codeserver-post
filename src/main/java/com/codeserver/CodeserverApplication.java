package com.codeserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CodeserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeserverApplication.class, args);
	}

}
