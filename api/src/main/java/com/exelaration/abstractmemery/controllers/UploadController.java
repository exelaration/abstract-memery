package com.exelaration.abstractmemery.controllers;

<<<<<<< HEAD
import com.exelaration.abstractmemery.domains.Image;
import com.exelaration.abstractmemery.services.ImageService;
=======
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.tomcat.util.codec.binary.Base64;
>>>>>>> make app run without compile errors but data does not save to database
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
@CrossOrigin(origins = "http://localhost:3000")
public class UploadController {
  @Autowired private ImageService imageService;

  @PostMapping("/")
  public Image uploadData(@RequestParam("file") MultipartFile file) throws Exception {

    return imageService.save(file);
  }
}
