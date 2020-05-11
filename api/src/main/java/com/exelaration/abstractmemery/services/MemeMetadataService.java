package com.exelaration.abstractmemery.services;

import com.exelaration.abstractmemery.domains.Meme;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface MemeMetadataService {
  public List<Meme> findAll(Pageable pageable);

  public Optional<Meme> findById(Integer id);

  public Meme save(Meme meme);

  public void deleteById(Integer id);

  public ArrayList<Meme> getUserMemes(int userId);

  public ArrayList<Meme> findByText(String text) throws Exception;

  public long getCount();
}
