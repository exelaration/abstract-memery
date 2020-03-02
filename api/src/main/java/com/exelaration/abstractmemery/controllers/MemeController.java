package com.exelaration.abstractmemery.controllers;

<<<<<<< HEAD
import com.exelaration.abstractmemery.domains.Meme;
import com.exelaration.abstractmemery.services.MemeService;
import org.springframework.beans.factory.annotation.Autowired;
=======
import com.exelaration.abstractmemery.domains.Caption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
>>>>>>> run goJF linter
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/meme")
@CrossOrigin(origins = "http://localhost:3000")
public class MemeController {

  @Autowired private MemeService memeService;

  @PostMapping("/")
  public Meme uploadMeme(@RequestBody Meme meme) throws Exception {

    return memeService.save(meme);
  }
}
