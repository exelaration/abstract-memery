package com.exelaration.abstractmemery.services;

import java.io.InputStream;

public interface FileStorageService {
  public void save(String fileName, InputStream file);
}
