package com.exelaration.abstractmemery.services;

import com.exelaration.abstractmemery.domains.Meme;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface MemeMetadataService {
  public List<Meme> findAll();

  public Optional<Meme> findById(Integer id);

  public Meme save(Meme meme);

  public void deleteById(Integer id);

  public ArrayList<Meme> getMemes();

  public ArrayList<Meme> findByText(String text) throws Exception;
}
