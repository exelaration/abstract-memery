package com.exelaration.abstractmemery.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.exelaration.abstractmemery.domains.Meme;
import com.exelaration.abstractmemery.repositories.MemeRepository;
import com.exelaration.abstractmemery.services.implementations.MemeMetadataServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class MemeMetadataServiceTest {
  @Mock private MemeRepository memeRepository;
  @InjectMocks private MemeMetadataServiceImpl memeMetadataService;

  @BeforeEach
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void findAll_WhenReturnsNull_expectEmptyPage() {
    Pageable pageable = PageRequest.of(0, 10);
    List<Meme> memes = new ArrayList<Meme>();
    Page<Meme> memeResponse = new PageImpl<Meme>(memes);

    Mockito.when(memeRepository.findAllByIsPublicTrueOrderByIdDesc(Mockito.any(Pageable.class)))
        .thenReturn(memeResponse);
    assertEquals(memeResponse.getContent(), memeMetadataService.findAll(pageable));
  }

  @Test
  public void getMemes_WhenReturnsSuccessfully_expectPage() {
    Pageable pageable = PageRequest.of(0, 10);
    List<Meme> memes = new ArrayList<Meme>();
    Meme testMeme = new Meme();
    testMeme.setMemeName("testMeme");
    memes.add(testMeme);
    Page<Meme> memeResponse = new PageImpl<Meme>(memes);

    Mockito.when(memeRepository.findAllByIsPublicTrueOrderByIdDesc(Mockito.any(Pageable.class)))
        .thenReturn(memeResponse);

    assertEquals(memeResponse.getContent(), memeMetadataService.findAll(pageable));
  }

  @Test
  public void getUserMemes__WhenReturnsNull_expectEmptyArray() {
    Mockito.when(memeRepository.findByUserIdOrderByIdDesc(1)).thenReturn(null);

    assertNull(memeMetadataService.getUserMemes(1));
  }

  @Test
  public void getUserMemes_WhenReturnsSuccessfully_expectArray() {
    ArrayList<Meme> expectedMemes = new ArrayList<Meme>();
    Meme testMeme = new Meme();
    testMeme.setMemeName("testMeme");
    expectedMemes.add(testMeme);
    String expectedFirstMemeName = expectedMemes.get(0).getMemeName();

    Mockito.when(memeRepository.findByUserIdOrderByIdDesc(1)).thenReturn(expectedMemes);
    String actualFirstMemeName = memeMetadataService.getUserMemes(1).get(0).getMemeName();

    assertEquals(expectedFirstMemeName, actualFirstMemeName);
  }
}
