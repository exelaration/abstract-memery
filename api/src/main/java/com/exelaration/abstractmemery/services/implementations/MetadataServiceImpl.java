package com.exelaration.abstractmemery.services.implementations;

import com.exelaration.abstractmemery.domains.Image;
import com.exelaration.abstractmemery.repositories.ImageRepository;
import com.exelaration.abstractmemery.services.MetadataService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("metadataService")
public class MetadataServiceImpl implements MetadataService {
  @Autowired private ImageRepository imageRepository;

  public List<Image> findAll() {
    return (List<Image>) imageRepository.findAll();
  }

  public Optional<Image> findById(Integer id) {
    return imageRepository.findById(id);
  }

  public Image save(Image image) {
    return imageRepository.save(image);
  }

  public void deleteById(Integer id) {
    imageRepository.deleteById(id);
  }
}
