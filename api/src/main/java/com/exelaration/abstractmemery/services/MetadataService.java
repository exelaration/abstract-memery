package com.exelaration.abstractmemery.services;

import com.exelaration.abstractmemery.domains.Image;
import java.util.List;
import java.util.Optional;

public interface MetadataService {

  public List<Image> findAll();

  public Optional<Image> findById(Integer id);

  public Image save(Image image);

  public void deleteById(Integer id);
}
