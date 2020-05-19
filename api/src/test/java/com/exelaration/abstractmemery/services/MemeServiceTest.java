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
import java.util.ArrayList;
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
    String fileType = "memes/";
    Meme mockMeme = new Meme();
    mockMeme.setMemeName(fileName);
    String expectedImageData = "image data";

    when(memeMetadataService.findById(5)).thenReturn(Optional.of(mockMeme));
    when(fileStorageService.getFileData(fileName, fileType)).thenReturn(expectedImageData);
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

  @Test
  public void getMemes_WhenFileStorageServiceThrowsException_expectNull() {
    int page = 0;
    ArrayList<Meme> expectedMemes = new ArrayList<Meme>();
    Meme testMeme = new Meme();
    testMeme.setMemeName("testMeme");
    expectedMemes.add(testMeme);
    Mockito.when(memeMetadataService.findAll(Mockito.any())).thenReturn(expectedMemes);
    doThrow(new RuntimeException())
        .when(fileStorageService)
        .getFileData(Mockito.any(), Mockito.any());
    assertNull(memeService.getMemes(page));
  }

  @Test
  public void getMemes_WhenSuccessful_expectMemeURLs() {
    int page = 0;
    ArrayList<Meme> expectedMemes = new ArrayList<Meme>();
    Meme testMeme = new Meme();
    testMeme.setMemeName("testMeme");
    expectedMemes.add(testMeme);
    String memeURL = "testMemeURL";

    ArrayList<String> expectedURLs = new ArrayList<String>();
    expectedURLs.add("testMemeURL");
    Mockito.when(memeMetadataService.findAll(Mockito.any())).thenReturn(expectedMemes);
    Mockito.when(fileStorageService.getFileData(Mockito.any(), Mockito.any())).thenReturn(memeURL);
    assertEquals(expectedURLs.get(0), memeService.getMemes(page).get(0).getMemeUrl());
  }

  @Test
  public void getMemes_WhenMemesDoNotExistLocally_expectNullMemes() {
    int page = 0;
    ArrayList<Meme> expectedMemes = new ArrayList<Meme>();
    Meme testMeme = new Meme();
    testMeme.setMemeName("testMeme");
    expectedMemes.add(testMeme);
    String memeURL = null;

    ArrayList<String> expectedURLs = new ArrayList<String>();
    expectedURLs.add(memeURL);
    Mockito.when(memeMetadataService.findAll(Mockito.any())).thenReturn(expectedMemes);
    Mockito.when(fileStorageService.getFileData(Mockito.any(), Mockito.any())).thenReturn(memeURL);
    assertEquals(expectedURLs.get(0), memeService.getMemes(page).get(0).getMemeUrl());
  }

  @Test
  public void getMemes_WhenSomeMemesExistLocally_expectSomeMemes() {
    String memeURL1 = "testMemeURL1";
    String memeURL2 = null;
    String memeURL3 = "testMemeURL3";
    String fileType = "memes/";

    ArrayList<Meme> expectedMemes = new ArrayList<Meme>();
    Meme expectedMeme1 = new Meme();
    expectedMeme1.setMemeName("testMeme1");
    expectedMeme1.setMemeUrl(memeURL1);
    expectedMemes.add(expectedMeme1);
    Meme expectedMeme2 = new Meme();
    expectedMeme2.setMemeName("testMeme2");
    expectedMeme2.setMemeUrl(memeURL2);
    expectedMemes.add(expectedMeme2);
    Meme expectedMeme3 = new Meme();
    expectedMeme3.setMemeName("testMeme3");
    expectedMeme3.setMemeUrl(memeURL3);
    expectedMemes.add(expectedMeme3);

    String expectedMemeUrl1 = expectedMeme1.getMemeUrl();
    String expectedMemeUrl2 = expectedMeme2.getMemeUrl();
    String expectedMemeUrl3 = expectedMeme3.getMemeUrl();

    Mockito.when(memeMetadataService.findAll(Mockito.any())).thenReturn(expectedMemes);
    Mockito.when(fileStorageService.getFileData(expectedMeme1.getMemeName(), fileType))
        .thenReturn(expectedMemeUrl1);
    Mockito.when(fileStorageService.getFileData(expectedMeme2.getMemeName(), fileType))
        .thenReturn(expectedMemeUrl2);
    Mockito.when(fileStorageService.getFileData(expectedMeme3.getMemeName(), fileType))
        .thenReturn(expectedMemeUrl3);

    int page = 0;
    ArrayList<Meme> actualMemes = memeService.getMemes(page);
    String actualMemeUrl1 = actualMemes.get(0).getMemeUrl();
    String actualMemeUrl2 = actualMemes.get(1).getMemeUrl();
    String actualMemeUrl3 = actualMemes.get(2).getMemeUrl();

    assertEquals(expectedMemeUrl1, actualMemeUrl1);
    assertEquals(expectedMemeUrl2, actualMemeUrl2);
    assertEquals(expectedMemeUrl3, actualMemeUrl3);
  }

  @Test
  public void getUserMemes_WhenFileStorageServiceThrowsException_expectNull() {
    int userId = 1;
    ArrayList<Meme> expectedMemes = new ArrayList<Meme>();
    Meme testMeme = new Meme();
    testMeme.setMemeName("testMeme");
    expectedMemes.add(testMeme);

    Mockito.when(memeMetadataService.getUserMemes(userId)).thenReturn(expectedMemes);
    doThrow(new RuntimeException())
        .when(fileStorageService)
        .getFileData(Mockito.any(), Mockito.any());
    assertNull(memeService.getUserMemes(userId));
  }

  @Test
  public void getUserMemes_WhenSuccessful_expectMemeURLs() {
    int userId = 1;
    ArrayList<Meme> expectedMemes = new ArrayList<Meme>();
    Meme testMeme = new Meme();
    testMeme.setMemeName("testMeme");
    expectedMemes.add(testMeme);
    String memeURL = "testMemeURL";
    ArrayList<String> expectedURLs = new ArrayList<String>();
    expectedURLs.add("testMemeURL");
    String expectedMemeUrl = expectedURLs.get(0);

    Mockito.when(memeMetadataService.getUserMemes(userId)).thenReturn(expectedMemes);
    Mockito.when(fileStorageService.getFileData(Mockito.any(), Mockito.any())).thenReturn(memeURL);
    String actualMemeUrl = memeService.getUserMemes(userId).get(0).getMemeUrl();

    assertEquals(expectedMemeUrl, actualMemeUrl);
  }

  @Test
  public void getUserMemes_WhenMemesDoNotExistLocally_expectNullMemes() {
    int userId = 1;
    ArrayList<Meme> expectedMemes = new ArrayList<Meme>();
    Meme testMeme = new Meme();
    testMeme.setMemeName("testMeme");
    expectedMemes.add(testMeme);
    String memeURL = null;
    ArrayList<String> expectedURLs = new ArrayList<String>();
    expectedURLs.add(memeURL);
    String expectedMemeUrl = expectedURLs.get(0);

    Mockito.when(memeMetadataService.getUserMemes(userId)).thenReturn(expectedMemes);
    Mockito.when(fileStorageService.getFileData(Mockito.any(), Mockito.any())).thenReturn(memeURL);
    String actualMemeUrl = memeService.getUserMemes(userId).get(0).getMemeUrl();

    assertEquals(expectedMemeUrl, actualMemeUrl);
  }

  @Test
  public void getUserMemes_WhenSomeMemesExistLocally_expectSomeMemes() {
    int userId = 1;
    String fileType = "memes/";
    String memeURL1 = "testMemeURL1";
    String memeURL2 = null;
    String memeURL3 = "testMemeURL3";
    ArrayList<Meme> expectedMemes = new ArrayList<Meme>();
    Meme expectedMeme1 = new Meme();
    expectedMeme1.setMemeName("testMeme1");
    expectedMeme1.setMemeUrl(memeURL1);
    expectedMemes.add(expectedMeme1);
    Meme expectedMeme2 = new Meme();
    expectedMeme2.setMemeName("testMeme2");
    expectedMeme2.setMemeUrl(memeURL2);
    expectedMemes.add(expectedMeme2);
    Meme expectedMeme3 = new Meme();
    expectedMeme3.setMemeName("testMeme3");
    expectedMeme3.setMemeUrl(memeURL3);
    expectedMemes.add(expectedMeme3);

    String expectedMemeUrl1 = expectedMeme1.getMemeUrl();
    String expectedMemeUrl2 = expectedMeme2.getMemeUrl();
    String expectedMemeUrl3 = expectedMeme3.getMemeUrl();

    Mockito.when(memeMetadataService.getUserMemes(userId)).thenReturn(expectedMemes);
    Mockito.when(fileStorageService.getFileData(expectedMeme1.getMemeName(), fileType))
        .thenReturn(expectedMemeUrl1);
    Mockito.when(fileStorageService.getFileData(expectedMeme2.getMemeName(), fileType))
        .thenReturn(expectedMemeUrl2);
    Mockito.when(fileStorageService.getFileData(expectedMeme3.getMemeName(), fileType))
        .thenReturn(expectedMemeUrl3);

    ArrayList<Meme> actualMemes = memeService.getUserMemes(userId);
    String actualMemeUrl1 = actualMemes.get(0).getMemeUrl();
    String actualMemeUrl2 = actualMemes.get(1).getMemeUrl();
    String actualMemeUrl3 = actualMemes.get(2).getMemeUrl();

    assertEquals(expectedMemeUrl1, actualMemeUrl1);
    assertEquals(expectedMemeUrl2, actualMemeUrl2);
    assertEquals(expectedMemeUrl3, actualMemeUrl3);
  }

  public void getMemesWithText_WhenFileStorageServiceThrowsException_expectNull() throws Exception {
    ArrayList<Meme> actualMemes = new ArrayList<Meme>();
    Meme testMeme = new Meme();
    testMeme.setTopText("test");
    actualMemes.add(testMeme);

    Mockito.when(memeMetadataService.findByText("test")).thenReturn(actualMemes);
    doThrow(new RuntimeException())
        .when(fileStorageService)
        .getFileData(Mockito.any(), Mockito.any());
    assertNull(memeService.getMemesWithText("test"));
  }

  @Test
  public void getMemesWithText_WhenSuccessful_expectMeme() throws Exception {
    ArrayList<Meme> actualMemes = new ArrayList<Meme>();
    String memeURL = "testMemeURL";
    Meme testMeme = new Meme();
    testMeme.setTopText("test");
    actualMemes.add(testMeme);

    Mockito.when(memeMetadataService.findByText("test")).thenReturn(actualMemes);
    Mockito.when(fileStorageService.getFileData(Mockito.any(), Mockito.any())).thenReturn(memeURL);
    assertEquals(memeURL, memeService.getMemesWithText("test").get(0).getMemeUrl());
  }

  @Test
  public void getMemesWithText_WhenMemesDoNotExistLocally_expectNullMemes() throws Exception {
    ArrayList<Meme> actualMemes = new ArrayList<Meme>();
    Meme testMeme = new Meme();
    testMeme.setMemeName("testMeme");
    actualMemes.add(testMeme);
    String memeURL = null;

    Mockito.when(memeMetadataService.findByText("test")).thenReturn(actualMemes);
    Mockito.when(fileStorageService.getFileData(Mockito.any(), Mockito.any())).thenReturn(memeURL);
    assertEquals(memeURL, memeService.getMemesWithText("test").get(0).getMemeUrl());
  }

  @Test
  public void getMemesWithText_WhenSomeMemesExistLocally_expectSomeMemes() throws Exception {
    ArrayList<Meme> actualMemes = new ArrayList<Meme>();
    Meme testMeme1 = new Meme();
    testMeme1.setTopText("test");
    actualMemes.add(testMeme1);
    Meme testMeme2 = new Meme();
    testMeme2.setTopText("test");
    actualMemes.add(testMeme2);
    Meme testMeme3 = new Meme();
    testMeme3.setBottomText("test");
    actualMemes.add(testMeme3);

    Meme meme1 = new Meme();
    meme1.setMemeUrl("testMemeURL1");
    Meme meme2 = new Meme();
    meme2.setMemeUrl("");
    Meme meme3 = new Meme();
    meme3.setMemeUrl("testMemeURL3");
    ArrayList<Meme> expectedMemes = new ArrayList<Meme>();
    expectedMemes.add(meme1);
    expectedMemes.add(meme2);
    expectedMemes.add(meme3);

    Mockito.when(memeMetadataService.findByText("test")).thenReturn(actualMemes);
    Mockito.when(fileStorageService.getFileData(Mockito.any(), Mockito.any()))
        .thenReturn(meme1.getMemeUrl())
        .thenReturn("")
        .thenReturn(meme3.getMemeUrl());
    ArrayList<Meme> memes = memeService.getMemesWithText("test");
    assertEquals(expectedMemes.get(0).getMemeUrl(), memes.get(0).getMemeUrl());
    assertEquals(expectedMemes.get(1).getMemeUrl(), memes.get(1).getMemeUrl());
  }

  @Test
  public void getMemes_WhenMemesDoNotExistInDB_expectNullMemes() {
    int page = 0;
    String expectedMessage = "Images Do Not Exist";
    Mockito.when(memeMetadataService.findAll(Mockito.any())).thenReturn(null);

    Exception exception =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              memeService.getMemes(page);
            });

    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  public void getMemesWithText_WhenInvalidSearch_ExpectNull() throws Exception {
    Mockito.when(memeMetadataService.findByText("t"))
        .thenThrow(
            new IllegalArgumentException("Search query must contain more than one character"));
    assertNull(memeService.getMemesWithText("t"));
  }
}
