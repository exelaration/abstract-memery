package com.exelaration.abstractmemery.services.implementations;

import com.exelaration.abstractmemery.domains.Image;
import com.exelaration.abstractmemery.services.FileStorageService;
import com.exelaration.abstractmemery.services.ImageService;
import com.exelaration.abstractmemery.services.MetadataService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("imageService")
public class ImageServiceImpl implements ImageService {

  @Autowired private FileStorageService fileStorageService;

  @Autowired private MetadataService metadataService;

  public Image save(MultipartFile file) {
    Image image = new Image();
    String fileName = "";
    String fileData = "";
    try {
      byte[] bytes = file.getBytes();
      fileData = Base64.encodeBase64String(bytes);
      fileName = file.getOriginalFilename();

      image.setFileData(fileData);
      image.setFileName(fileName);

    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
      fileStorageService.save(fileName, fileData, "images/");
      return metadataService.save(image);

    } catch (Exception e) {
      return null;
    }
  }
}
