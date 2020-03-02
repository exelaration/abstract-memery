package com.exelaration.abstractmemery.services;

import com.exelaration.abstractmemery.domains.Image;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

  public static final String uploadingDir = "/app/src/main/resources/images/";

  public Image save(MultipartFile file);
}
