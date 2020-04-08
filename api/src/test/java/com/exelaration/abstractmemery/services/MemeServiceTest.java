package com.exelaration.abstractmemery.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import com.exelaration.abstractmemery.domains.Meme;
import com.exelaration.abstractmemery.services.implementations.MemeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class MemeServiceTest {

  @Mock private FileStorageService fileStorageService;

  @Mock private MemeMetadataService memeMetadataService;

  @InjectMocks private MemeServiceImpl memeService;

  @BeforeEach
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void save_WhenMemeSavedSuccessfully_ExpectMemeReturned() {
    Meme mockMeme = new Meme();
    mockMeme.setMemeName("test-image");
    mockMeme.setMemeUrl("data:image/png;base64,imageData");

    Meme expectedMeme = new Meme();
    expectedMeme.setMemeName("test-image.jpg");
    expectedMeme.setMemeUrl("imageData");

    Mockito.when(memeMetadataService.save(Mockito.any())).thenReturn(expectedMeme);

    Meme actualMeme = memeService.save(mockMeme);

    assertEquals(expectedMeme.getMemeName(), actualMeme.getMemeName());
    assertEquals(expectedMeme.getMemeUrl(), actualMeme.getMemeUrl());

    verify(fileStorageService)
        .save(Mockito.eq("test-image.jpg"), Mockito.any(), Mockito.eq("memes/"));
  }

  @Test
  public void memeServiceSave_WhenMetadataSaveUnsuccessful_ExpectNull() {
    Meme mockMeme = new Meme();
    mockMeme.setMemeName("test-image");
    mockMeme.setTopText("top text");
    mockMeme.setBottomText("bottom text");
    mockMeme.setMemeUrl("data:image/png;base64,imageData");

    Mockito.when(memeMetadataService.save(Mockito.any())).thenThrow(new IllegalArgumentException());
    assertNull(memeService.save(mockMeme));
  }

  @Test
  public void memeServiceSave_WhenFileStorageSaveUnsuccessful_ExpectNull() {
    Meme mockMeme = new Meme();
    mockMeme.setMemeName("test-image");
    mockMeme.setTopText("top text");
    mockMeme.setBottomText("bottom text");
    mockMeme.setMemeUrl("data:image/png;base64,imageData");

    doThrow(new RuntimeException())
        .when(fileStorageService)
        .save(Mockito.any(), Mockito.any(), Mockito.any());
    assertNull(memeService.save(mockMeme));
  }
}
