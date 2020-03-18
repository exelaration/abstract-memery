package com.exelaration.abstractmemery.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import com.exelaration.abstractmemery.domains.Image;
import com.exelaration.abstractmemery.services.implementations.FileStorageServiceImpl;
import com.exelaration.abstractmemery.services.implementations.ImageServiceImpl;
import com.exelaration.abstractmemery.services.implementations.MetadataServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
public class ImageServiceTest {

    @Mock
    private FileStorageService fileStorageService;

    @Mock
    private MetadataService metadataService;

    @InjectMocks
    private ImageServiceImpl imageService;

    @Test
    public void testSaveImageSuccessfully() throws IOException {
        //look into moving this stuff into a set up method with @before annotation
        MockMultipartFile mockMultipartFile = new MockMultipartFile("user-image", "test-image.png",
            "image/png", "imageData".getBytes());

        Image expectedImage = new Image();
        expectedImage.setFileName("test-image.png");
        expectedImage.setFileData("imageData");
        expectedImage.setFileLocation("fileLocation");

        Mockito.when(metadataService.save(expectedImage)).thenReturn(expectedImage);
        Mockito.when(fileStorageService.save("test-image.png", mockMultipartFile.getInputStream())).thenReturn("fileLocation");

        Image actualImage = imageService.save(mockMultipartFile);

        assertEquals(expectedImage, actualImage);

    }
}