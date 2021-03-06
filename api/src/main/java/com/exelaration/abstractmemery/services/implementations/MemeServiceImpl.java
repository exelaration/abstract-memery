package com.exelaration.abstractmemery.services.implementations;

import com.exelaration.abstractmemery.domains.Meme;
import com.exelaration.abstractmemery.services.FileStorageService;
import com.exelaration.abstractmemery.services.MemeMetadataService;
import com.exelaration.abstractmemery.services.MemeService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service("memeService")
public class MemeServiceImpl implements MemeService {

  @Autowired private MemeMetadataService memeMetadataService;
  @Autowired private FileStorageService fileStorageService;

  private final String fileType = "memes/";

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
      memeData = fileStorageService.getFileData(memeName, fileType);
    } catch (Exception e) {
      return null;
    }
    return memeData;
  }

  public ArrayList<Meme> getMemes(int page) {
    Pageable pageable = PageRequest.of(page, 10);
    List<Meme> memes = memeMetadataService.findAll(pageable);
    return getMemesFromFileSystem(memes);
  }

  public ArrayList<Meme> getUserMemes(int userId) {
    ArrayList<Meme> userMemes = memeMetadataService.getUserMemes(userId);
    return getMemesFromFileSystem(userMemes);
  }

  private ArrayList<Meme> getMemesFromFileSystem(List<Meme> memes) {
    if (memes == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Images Do Not Exist");
    }
    ArrayList<Meme> memesWithData = new ArrayList<Meme>();
    for (Meme meme : memes) {
      try {
        String memeData = fileStorageService.getFileData(meme.getMemeName(), fileType);
        meme.setMemeUrl(memeData);
        memesWithData.add(meme);
      } catch (Exception e) {
        return null;
      }
    }
    return memesWithData;
  }

  public ArrayList<Meme> getMemesWithText(String text) {
    try {
      ArrayList<Meme> memes = memeMetadataService.findByText(text);
      if (memes == null) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Images Do Not Exist");
      }
      ArrayList<Meme> memesWithData = new ArrayList<Meme>();
      for (Meme meme : memes) {
        try {
          String memeData = fileStorageService.getFileData(meme.getMemeName(), fileType);
          meme.setMemeUrl(memeData);
          memesWithData.add(meme);
        } catch (Exception e) {
          return null;
        }
      }
      return memesWithData;
    } catch (Exception e) {
      return null;
    }
  }

  public int getCount() {
    return (int) memeMetadataService.getCount();
  }
}
