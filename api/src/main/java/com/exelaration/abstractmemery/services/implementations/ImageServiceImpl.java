package com.exelaration.abstractmemery.services.implementations;

import com.exelaration.abstractmemery.domains.Image;
import com.exelaration.abstractmemery.services.FileStorageService;
import com.exelaration.abstractmemery.services.ImageService;
import com.exelaration.abstractmemery.services.MetadataService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service("imageService")
public class ImageServiceImpl implements ImageService {

  @Autowired private FileStorageService fileStorageService;

  @Autowired private MetadataService metadataService;

  private final String fileType = "images/";

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
      String timeStamp = new Date().getTime() + "";
      int index = fileName.indexOf(".");
      fileName = fileName.substring(0, index) + "-" + timeStamp + fileName.substring(index);
      image.setFileName(fileName);
      fileStorageService.save(fileName, fileData, "images/");
      return metadataService.save(image);

    } catch (Exception e) {
      return null;
    }
  }

  public ArrayList<Image> getImages() {
    List<Image> images = metadataService.findAll();
    return getImagesFromFileSystem(images);
  }

  private ArrayList<Image> getImagesFromFileSystem(List<Image> images) {
    if (images == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Images Do Not Exist");
    }
    ArrayList<Image> imagesWithData = new ArrayList<Image>();
    for (Image image : images) {
      try {
        String imageData = fileStorageService.getFileData(image.getFileName(), fileType);
        image.setFileData(imageData);
        imagesWithData.add(image);
      } catch (Exception e) {
        return null;
      }
    }
    return imagesWithData;
  }
}
