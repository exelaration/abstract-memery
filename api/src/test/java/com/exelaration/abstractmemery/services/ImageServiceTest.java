package com.exelaration.abstractmemery.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import com.exelaration.abstractmemery.domains.Image;
import com.exelaration.abstractmemery.services.implementations.ImageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ImageServiceTest {

  @Mock private FileStorageService fileStorageService;

  @Mock private MetadataService metadataService;

  @InjectMocks private ImageServiceImpl imageService;

  @BeforeEach
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void save_WhenImageSavedSuccessfully_ExpectImageReturned() {
    MockMultipartFile mockMultipartFile =
        new MockMultipartFile(
            "user-image", "test-image.png", "image/png", "test-image.png".getBytes());

    Image expectedImage = new Image();
    expectedImage.setFileName("test-image.png");
    expectedImage.setFileData("imageData");

    Mockito.when(metadataService.save(Mockito.any())).thenReturn(expectedImage);

    Image actualImage = imageService.save(mockMultipartFile);

    assertEquals(expectedImage.getFileName(), actualImage.getFileName());
    verify(fileStorageService)
        .save(Mockito.eq("test-image.png"), Mockito.any(), Mockito.eq("images/"));
  }

  @Test
  public void save_WhenMetadataSaveUnsuccessful_ExpectNull() {
    MockMultipartFile mockMultipartFile =
        new MockMultipartFile(
            "user-image", "test-image.png", "image/png", "test-image.png".getBytes());

    Mockito.when(metadataService.save(Mockito.any())).thenThrow(new IllegalArgumentException());
    assertNull(imageService.save(mockMultipartFile));
  }

  @Test
  public void save_WhenFileStorageSaveUnsuccessful_ExpectNull() {
    MockMultipartFile mockMultipartFile =
        new MockMultipartFile(
            "user-image", "test-image.png", "image/png", "test-image.png".getBytes());

    doThrow(new RuntimeException())
        .when(fileStorageService)
        .save(Mockito.any(), Mockito.any(), Mockito.any());
    assertNull(imageService.save(mockMultipartFile));
  }
}
