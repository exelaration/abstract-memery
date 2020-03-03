package com.exelaration.abstractmemery.services;

import com.exelaration.abstractmemery.domains.Image;
import javax.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
public interface ImageService {

  public Image save(MultipartFile file);
}
