package com.exelaration.abstractmemery.services.implementations;

import com.exelaration.abstractmemery.services.FileStorageService;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.stereotype.Service;

@Service("fileStorageService")
public class FileStorageServiceImpl implements FileStorageService {
  private static final String uploadingDir = "/app/src/main/resources/images/";

  public void save(String fileName, InputStream fileStream) {
    String fileLocation = "";
    createDirectory();
    if (fileName.equals("")) {
      throw new RuntimeException("You must select the a file for uploading");
    }
    try {
      fileLocation = (uploadingDir + fileName);
      Path path = Paths.get(fileLocation);
      Files.write(path, fileStream.readAllBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void createDirectory() {
    new File(uploadingDir).mkdirs();
  }
}
