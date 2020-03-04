package com.exelaration.abstractmemery.services;

import com.exelaration.abstractmemery.domains.Image;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
  public Image save(MultipartFile file);
}
