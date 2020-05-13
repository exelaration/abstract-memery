package com.exelaration.abstractmemery.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.exelaration.abstractmemery.domains.Meme;
import com.exelaration.abstractmemery.repositories.MemeRepository;
import com.exelaration.abstractmemery.services.implementations.MemeMetadataServiceImpl;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class MemeMetadataServiceTest {
  @Mock private MemeRepository memeRepository;
  @InjectMocks private MemeMetadataServiceImpl memeMetadataService;

  @BeforeEach
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void getMemes_WhenReturnsNull_expectEmptyArray() {
    Mockito.when(memeRepository.findTop10ByOrderByIdDesc()).thenReturn(null);

    assertNull(memeMetadataService.getMemes());
  }

  @Test
  public void getMemes_WhenReturnsSuccessfully_expectArray() {
    ArrayList<Meme> expectedMemes = new ArrayList<Meme>();
    Meme testMeme = new Meme();
    testMeme.setMemeName("testMeme");
    expectedMemes.add(testMeme);

    Mockito.when(memeRepository.findTop10ByOrderByIdDesc()).thenReturn(expectedMemes);

    assertEquals(
        expectedMemes.get(0).getMemeName(), memeMetadataService.getMemes().get(0).getMemeName());
  }

  @Test
  public void getUserMemes__WhenReturnsNull_expectEmptyArray() {
    Mockito.when(memeRepository.findByUserId(1)).thenReturn(null);

    assertNull(memeMetadataService.getUserMemes(1));
  }

  @Test
  public void getUserMemes_WhenReturnsSuccessfully_expectArray() {
    ArrayList<Meme> expectedMemes = new ArrayList<Meme>();
    Meme testMeme = new Meme();
    testMeme.setMemeName("testMeme");
    expectedMemes.add(testMeme);
    String expectedFirstMemeName = expectedMemes.get(0).getMemeName();

    Mockito.when(memeRepository.findByUserId(1)).thenReturn(expectedMemes);
    String actualFirstMemeName = memeMetadataService.getUserMemes(1).get(0).getMemeName();

    assertEquals(expectedFirstMemeName, actualFirstMemeName);
  }
}
