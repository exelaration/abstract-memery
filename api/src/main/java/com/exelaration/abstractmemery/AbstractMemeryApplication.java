package com.exelaration.abstractmemery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.File;

import com.exelaration.abstractmemery.services.FileStorageService;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableJpaAuditing
public class AbstractMemeryApplication {

	public static void main(String[] args) {
		new File(FileStorageService.uploadingDir).mkdirs();
		SpringApplication.run(AbstractMemeryApplication.class, args);
	}

}