package com.exelaration.abstractmemery.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.exelaration.abstractmemery.domains.Meme;
import com.exelaration.abstractmemery.repositories.MemeRepository;
import com.exelaration.abstractmemery.services.implementations.MemeServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(SpringExtension.class)
public class MemeServiceTest {

  @Mock private FileStorageService fileStorageService;

  @Mock private MemeMetadataService memeMetadataService;

  @Mock private MemeRepository memeRepository;

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
  public void save_WhenMetadataSaveUnsuccessful_ExpectNull() {
    Meme mockMeme = new Meme();
    mockMeme.setMemeName("test-image");
    mockMeme.setTopText("top text");
    mockMeme.setBottomText("bottom text");
    mockMeme.setMemeUrl("data:image/png;base64,imageData");

    Mockito.when(memeMetadataService.save(Mockito.any())).thenThrow(new IllegalArgumentException());
    assertNull(memeService.save(mockMeme));
  }

  @Test
  public void save_WhenFileStorageSaveUnsuccessful_ExpectNull() {
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

  @Test
  public void getMeme_WhenMemeExists_ExpectMemeDisplayed() {
    String fileName = "test-file";
    Meme mockMeme = new Meme();
    mockMeme.setMemeName(fileName);
    String expectedImageData = "image data";

    when(memeMetadataService.findById(5)).thenReturn(Optional.of(mockMeme));
    when(fileStorageService.getFileData(fileName)).thenReturn(expectedImageData);
    String actualImageData = memeService.getMeme(5);

    assertEquals(expectedImageData, actualImageData);
  }

  @Test
  public void getMeme_WhenMemeDoesNotExistInDB_ExpectError() {
    Optional<Meme> emptyMeme = Optional.empty();
    String expectedMessage = "Image does not exist";

    when(memeMetadataService.findById(5)).thenReturn(emptyMeme);
    Exception exception =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              memeService.getMeme(5);
            });
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  public void getMeme_WhenMemeDoesNotExistLocally_ExpectNullImage() {
    String fileName = "test-file";
    Meme mockMeme = new Meme();
    mockMeme.setMemeName(fileName);
    String expectedImageData = null;

    when(memeMetadataService.findById(5)).thenReturn(Optional.of(mockMeme));
    String actualImageData = memeService.getMeme(5);

    assertEquals(expectedImageData, actualImageData);
  }
}
