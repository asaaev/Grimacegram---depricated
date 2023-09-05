package com.grimacegram.grimacegram;

import lombok.Builder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)

public class GrimacegramApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrimacegramApplication.class, args);
	}

}
