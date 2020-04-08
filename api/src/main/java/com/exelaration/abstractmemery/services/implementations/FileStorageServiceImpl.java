package com.exelaration.abstractmemery.services.implementations;

import com.exelaration.abstractmemery.services.FileStorageService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import org.springframework.stereotype.Service;

@Service("fileStorageService")
public class FileStorageServiceImpl implements FileStorageService {

  public void save(String fileName, String urlData, String storageLocation) {
    String uploadingDir = "/app/src/main/resources/" + storageLocation;
    createDirectory(uploadingDir);
    String fileLocation = "";
    if (fileName.equals("")) {
      throw new RuntimeException("You must select the a file for uploading");
    }
    try {
      fileLocation = (uploadingDir + fileName);
      Path path = Paths.get(fileLocation);
      byte[] decodedBytes = Base64.getDecoder().decode(urlData);
      Files.write(path, decodedBytes);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void createDirectory(String uploadDirectory) {
    new File(uploadDirectory).mkdirs();
  }
}
