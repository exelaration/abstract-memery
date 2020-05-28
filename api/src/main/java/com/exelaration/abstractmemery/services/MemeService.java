package com.exelaration.abstractmemery.services;

import com.exelaration.abstractmemery.domains.Meme;
import java.util.ArrayList;
import javax.transaction.Transactional;

@Transactional
public interface MemeService {

  public Meme save(Meme meme);

  public String getMeme(int id);

  public ArrayList<Meme> getMemes(int page);

  public ArrayList<Meme> getUserMemes(int userId);

  public ArrayList<Meme> getMemesWithText(String text);

  public ArrayList<Meme> getMemesForProfilePage(String username);

  public int getCount();
}
