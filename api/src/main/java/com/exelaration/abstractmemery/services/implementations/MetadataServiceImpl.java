package com.exelaration.abstractmemery.services.implementations;

import com.exelaration.abstractmemery.domains.Image;
import com.exelaration.abstractmemery.repositories.MemeRepository;
import com.exelaration.abstractmemery.services.MetadataService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("metadataService")
public class MetadataServiceImpl implements MetadataService {
  @Autowired private MemeRepository memeRepository;

  public List<Image> findAll() {
    return (List<Image>) memeRepository.findAll();
  }

  public Optional<Image> findById(Long id) {
    return memeRepository.findById(id);
  }

  public Image save(Image image) {
    return memeRepository.save(image);
  }

  public void deleteById(Long id) {
    memeRepository.deleteById(id);
  }
}
