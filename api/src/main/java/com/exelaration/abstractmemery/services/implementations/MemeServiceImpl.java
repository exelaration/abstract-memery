package com.exelaration.abstractmemery.services.implementations;

import com.exelaration.abstractmemery.domains.Meme;
import com.exelaration.abstractmemery.services.MemeMetadataService;
import com.exelaration.abstractmemery.services.MemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("memeService")
public class MemeServiceImpl implements MemeService {

  @Autowired MemeMetadataService memeMetadataService;

  public Meme save(Meme meme) {
    return memeMetadataService.save(meme);
  }
}
