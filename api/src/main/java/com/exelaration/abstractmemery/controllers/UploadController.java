package com.exelaration.abstractmemery.controllers;

import com.exelaration.abstractmemery.domains.Image;
import com.exelaration.abstractmemery.services.ImageService;
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
