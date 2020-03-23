package com.exelaration.abstractmemery.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
<<<<<<< HEAD
=======
import static org.junit.jupiter.api.Assertions.assertNotNull;
>>>>>>> Fixed unit test for Image service
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import com.exelaration.abstractmemery.domains.Image;
import com.exelaration.abstractmemery.services.implementations.ImageServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ImageServiceTest {

    @Mock
    private FileStorageService fileStorageService;

    @Mock
    private MetadataService metadataService;

    @InjectMocks
    private ImageServiceImpl imageService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveImageSuccessfully() throws IOException {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("user-image", "test-image.png",
            "image/png", "test-image.png".getBytes());
        
        Image expectedImage = new Image();
        expectedImage.setFileName("test-image.png");
        expectedImage.setFileData("imageData");

        Mockito.when(metadataService.save(Mockito.any())).thenReturn(expectedImage);

        Image actualImage = imageService.save(mockMultipartFile);
        
        assertEquals(expectedImage.getFileName(), actualImage.getFileName());
        verify(fileStorageService).save(Mockito.eq("test-image.png"), Mockito.any());
    }
}