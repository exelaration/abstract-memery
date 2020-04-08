package com.exelaration.abstractmemery.services.implementations;

import com.exelaration.abstractmemery.domains.Meme;
import com.exelaration.abstractmemery.services.FileStorageService;
import com.exelaration.abstractmemery.services.MemeMetadataService;
import com.exelaration.abstractmemery.services.MemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
