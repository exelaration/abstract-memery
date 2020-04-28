package com.exelaration.abstractmemery.controllers;

import com.exelaration.abstractmemery.domains.Meme;
import com.exelaration.abstractmemery.services.MemeService;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/meme")
public class MemeController {

  @Autowired private MemeService memeService;

  @PostMapping("/")
  public Meme uploadMeme(@RequestBody Meme meme) throws Exception {

    return memeService.save(meme);
  }

  @GetMapping(value = "/", params = "id")
  public String getMeme(@RequestParam int id) throws Exception {
    return memeService.getMeme(id);
  }

  @GetMapping()
  public ArrayList<Meme> getMemesForGallery() {
    return memeService.getMemes();
  }

  @GetMapping(value = "/", params = "text")
  public ArrayList<Meme> getMemesForSearch(@RequestParam String text) throws Exception {
    return memeService.getMemesWithText(text);
  }
}
