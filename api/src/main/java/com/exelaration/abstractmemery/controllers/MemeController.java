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

  @GetMapping(value = "/", params = "page")
  public ArrayList<Meme> getMemesForGallery(@RequestParam int page) {
    return memeService.getMemes(page);
  }

  @GetMapping(value = "/", params = "userId")
  public ArrayList<Meme> getMemesForUserGallery(@RequestParam int userId) throws Exception {
    return memeService.getUserMemes(userId);
  }

  @GetMapping(value = "/", params = "text")
  public ArrayList<Meme> getMemesForSearch(@RequestParam String text) throws Exception {
    return memeService.getMemesWithText(text);
  }

  @GetMapping(value = "/count")
  public int getMemeCount() {
    return memeService.getCount();
  }

  @GetMapping(value = "/", params = "username")
  public ArrayList<Meme> getMemesForProfilePage(@RequestParam String username) throws Exception {
    return memeService.getMemesForProfilePage(username);
  }
}
