package com.exelaration.abstractmemery.services.implementations;

import com.exelaration.abstractmemery.domains.Meme;
import com.exelaration.abstractmemery.services.FileStorageService;
import com.exelaration.abstractmemery.services.MemeMetadataService;
import com.exelaration.abstractmemery.services.MemeService;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service("memeService")
public class MemeServiceImpl implements MemeService {

  @Autowired private MemeMetadataService memeMetadataService;
  @Autowired private FileStorageService fileStorageService;

  public Meme save(Meme meme) {
    try {
      String memeUrl = meme.getMemeUrl();
      memeUrl = memeUrl.replaceAll("data:image/png;base64,", "");
      meme.setMemeName(meme.getMemeName() + ".jpg");

      fileStorageService.save(meme.getMemeName(), memeUrl, "memes/");
      return memeMetadataService.save(meme);

    } catch (Exception e) {
      return null;
    }
  }

  public String getMeme(int id) {
    Optional<Meme> meme = memeMetadataService.findById(id);
    String memeData = "";
    if (meme.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image does not exist");
    }
    try {
      String memeName = meme.get().getMemeName();
      memeData = fileStorageService.getFileData(memeName);
    } catch (Exception e) {
      return null;
    }
    return memeData;
  }

  public ArrayList<String> getMemes() {
    ArrayList<String> memeNames = memeMetadataService.getMemes();
    if (memeNames == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Images Do Not Exist");
    }
    ArrayList<String> memeURLs = new ArrayList<String>();
    for (String memeName : memeNames) {
      try {
        String meme = fileStorageService.getFileData(memeName);
        memeURLs.add(meme);
      } catch (Exception e) {
        return null;
      }
    }
    return memeURLs;
  }
}
