package com.exelaration.abstractmemery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
// import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.File;

import com.exelaration.abstractmemery.controllers.UploadController;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableJpaAuditing
// @EnableJpaRepositories
public class AbstractMemeryApplication {

	public static void main(String[] args) {
		new File(UploadController.uploadingDir).mkdirs();
		SpringApplication.run(AbstractMemeryApplication.class, args);
	}

}