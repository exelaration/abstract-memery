package com.exelaration.abstractmemery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class AbstractMemeryApplication {

	public static void main(String[] args) {
		SpringApplication.run(AbstractMemeryApplication.class, args);
	}

}