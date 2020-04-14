package com.exelaration.abstractmemery.services;

import com.exelaration.abstractmemery.domains.Meme;
import java.util.ArrayList;
import javax.transaction.Transactional;

@Transactional
public interface MemeService {

  public Meme save(Meme meme);

  public String getMeme(int id);

  public ArrayList<String> getMemes();
}
