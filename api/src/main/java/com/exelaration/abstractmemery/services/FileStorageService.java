package com.exelaration.abstractmemery.services;

public interface FileStorageService {

  public void save(String fileName, String urlData, String storageLocation);

  public String getFileData(String fileName, String fileType);
}
