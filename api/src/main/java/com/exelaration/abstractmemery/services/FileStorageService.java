package com.exelaration.abstractmemery.services;

import java.io.InputStream;

public interface FileStorageService {
  public String save(String fileName, InputStream file);
}
