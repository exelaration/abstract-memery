package com.exelaration.abstractmemery.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
    Mockito.when(memeRepository.getMemes()).thenReturn(null);

    assertNull(memeMetadataService.getMemes());
  }

  @Test
  public void getMemes_WhenReturnsSuccessfully_expectArray() {
    ArrayList<String> memeNames = new ArrayList<String>();
    memeNames.add("testMemeName");

    Mockito.when(memeRepository.getMemes()).thenReturn(memeNames);

    assertEquals(memeNames, memeMetadataService.getMemes());
  }
}
