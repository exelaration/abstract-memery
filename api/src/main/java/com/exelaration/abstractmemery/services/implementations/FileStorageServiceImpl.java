package com.exelaration.abstractmemery.services.implementations;

import com.exelaration.abstractmemery.domains.Image;
import com.exelaration.abstractmemery.services.FileStorageService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("fileStorageService")
public class FileStorageServiceImpl implements FileStorageService {
  public Image save(MultipartFile file) {
    if (file == null) {
      throw new RuntimeException("You must select the a file for uploading");
    }
    Image image = new Image();
    try {
      // saves the image
      byte[] bytes = file.getBytes();
      String fileName = file.getOriginalFilename();
      Path path = Paths.get(uploadingDir + fileName);
      Files.write(path, bytes);

      String fileData = Base64.encodeBase64String(bytes);
      image.setFileData(fileData);
      image.setFileLocation(uploadingDir + fileName);
      image.setFileName(fileName);

    } catch (IOException e) {
      e.printStackTrace();
    }
    return image;
  }
}
