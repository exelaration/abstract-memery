package com.exelaration.abstractmemery.services.implementations;

import com.exelaration.abstractmemery.domains.Image;
import com.exelaration.abstractmemery.services.FileStorageService;
import com.exelaration.abstractmemery.services.ImageService;
import com.exelaration.abstractmemery.services.MetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("imageService")
public class ImageServiceImpl implements ImageService {

  @Autowired private FileStorageService fileStorageService;

  @Autowired private MetadataService metadataService;

  public Image save(MultipartFile file) {
    Image image = fileStorageService.save(file);

    return metadataService.save(image);
  }
}
