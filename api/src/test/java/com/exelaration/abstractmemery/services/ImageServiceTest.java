package com.exelaration.abstractmemery.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import com.exelaration.abstractmemery.domains.Image;
import com.exelaration.abstractmemery.services.implementations.ImageServiceImpl;
import java.util.ArrayList;
import java.util.Date;
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
    String timeStamp = new Date().getTime() + "";
    String fileName = "test-image-" + timeStamp + ".png";
    MockMultipartFile mockMultipartFile =
        new MockMultipartFile("user-image", fileName, "image/png", fileName.getBytes());

    Image expectedImage = new Image();

    expectedImage.setFileName(fileName);
    expectedImage.setFileData("imageData");

    Mockito.when(metadataService.save(Mockito.any())).thenReturn(expectedImage);

    Image actualImage = imageService.save(mockMultipartFile);

    assertEquals(expectedImage.getFileName(), actualImage.getFileName());
    verify(fileStorageService).save(Mockito.any(), Mockito.any(), Mockito.eq("images/"));
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

  public void getImages_WhenFileStorageServiceThrowsException_expectNull() {
    ArrayList<Image> expectedImages = new ArrayList<Image>();
    Image testImage = new Image();
    testImage.setFileName("testImage");
    expectedImages.add(testImage);

    Mockito.when(metadataService.findAll()).thenReturn(expectedImages);
    doThrow(new RuntimeException())
        .when(fileStorageService)
        .getFileData(Mockito.any(), Mockito.any());
    assertNull(imageService.getImages());
  }

  @Test
  public void getImages_WhenSuccessful_expectImageURLs() {
    ArrayList<Image> expectedImages = new ArrayList<Image>();
    Image testImage = new Image();
    testImage.setFileName("testImage");
    expectedImages.add(testImage);
    String imageURL = "testImageURL";

    ArrayList<String> expectedURLs = new ArrayList<String>();
    expectedURLs.add("testImageURL");
    Mockito.when(metadataService.findAll()).thenReturn(expectedImages);
    Mockito.when(fileStorageService.getFileData(Mockito.any(), Mockito.any())).thenReturn(imageURL);
    assertEquals(expectedURLs.get(0), imageService.getImages().get(0).getFileData());
  }

  @Test
  public void getImages_WhenImagesDoNotExistLocally_expectNullImages() {
    ArrayList<Image> expectedImages = new ArrayList<Image>();
    Image testImage = new Image();
    testImage.setFileName("testImage");
    expectedImages.add(testImage);
    String imageURL = null;

    ArrayList<String> expectedURLs = new ArrayList<String>();
    expectedURLs.add(imageURL);
    Mockito.when(metadataService.findAll()).thenReturn(expectedImages);
    Mockito.when(fileStorageService.getFileData(Mockito.any(), Mockito.any())).thenReturn(imageURL);
    assertEquals(expectedURLs.get(0), imageService.getImages().get(0).getFileData());
  }

  @Test
  public void getImages_WhenSomeImagesExistLocally_expectSomeImages() {
    String imageURL1 = "testImageURL1";
    String imageURL2 = null;
    String imageURL3 = "testImageURL3";
    String fileType = "images/";

    ArrayList<Image> expectedImages = new ArrayList<Image>();
    Image expectedImage1 = new Image();
    expectedImage1.setFileName("testImage1");
    expectedImage1.setFileData(imageURL1);
    expectedImages.add(expectedImage1);
    Image expectedImage2 = new Image();
    expectedImage2.setFileName("testImage2");
    expectedImage2.setFileData(imageURL2);
    expectedImages.add(expectedImage2);
    Image expectedImage3 = new Image();
    expectedImage3.setFileName("testImage3");
    expectedImage3.setFileData(imageURL3);
    expectedImages.add(expectedImage3);

    String expectedImageUrl1 = expectedImage1.getFileData();
    String expectedImageUrl2 = expectedImage2.getFileData();
    String expectedImageUrl3 = expectedImage3.getFileData();

    Mockito.when(metadataService.findAll()).thenReturn(expectedImages);
    Mockito.when(fileStorageService.getFileData(expectedImage1.getFileName(), fileType))
        .thenReturn(expectedImageUrl1);
    Mockito.when(fileStorageService.getFileData(expectedImage2.getFileName(), fileType))
        .thenReturn(expectedImageUrl2);
    Mockito.when(fileStorageService.getFileData(expectedImage3.getFileName(), fileType))
        .thenReturn(expectedImageUrl3);

    ArrayList<Image> actualImages = imageService.getImages();
    String actualImageUrl1 = actualImages.get(0).getFileData();
    String actualImageUrl2 = actualImages.get(1).getFileData();
    String actualImageUrl3 = actualImages.get(2).getFileData();

    assertEquals(expectedImageUrl1, actualImageUrl1);
    assertEquals(expectedImageUrl2, actualImageUrl2);
    assertEquals(expectedImageUrl3, actualImageUrl3);
  }
}
