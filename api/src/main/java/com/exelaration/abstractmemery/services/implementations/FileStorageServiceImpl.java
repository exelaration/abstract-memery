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
  private String uploadingDir = "/app/src/main/resources/";

  public void save(String fileName, String urlData, String storageLocation) {
    String uploadLocation = uploadingDir + storageLocation;
    createDirectory(uploadLocation);
    String fileLocation = "";
    if (fileName.equals("")) {
      throw new RuntimeException("You must select the a file for uploading");
    }
    try {
      fileLocation = (uploadLocation + fileName);
      Path path = Paths.get(fileLocation);
      byte[] decodedBytes = Base64.getDecoder().decode(urlData);
      Files.write(path, decodedBytes);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String getFileData(String fileName, String fileType) {
    String fileLocation = uploadingDir + fileType;
    File file = new File(fileLocation + fileName);
    String fileData = "";
    if (file.exists()) {
      Path filePath = Paths.get(fileLocation + fileName);
      byte[] fileBytes;
      try {
        fileBytes = Files.readAllBytes(filePath);
        fileData = Base64.getEncoder().encodeToString(fileBytes);
      } catch (IOException e) {
        System.out.println("Image not found!");
        return null;
      }
    }
    return fileData;
  }

  private void createDirectory(String uploadDirectory) {
    new File(uploadDirectory).mkdirs();
  }
}
