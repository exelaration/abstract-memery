package com.exelaration.abstractmemery;

import com.exelaration.abstractmemery.services.FileStorageService;
import java.io.File;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableJpaAuditing
public class AbstractMemeryApplication {

  public static void main(String[] args) {
    new File(FileStorageService.uploadingDir).mkdirs();
    SpringApplication.run(AbstractMemeryApplication.class, args);
  }
}
