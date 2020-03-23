package com.exelaration.abstractmemery.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import com.exelaration.abstractmemery.domains.Image;
import com.exelaration.abstractmemery.services.implementations.ImageServiceImpl;

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
        MockMultipartFile mockMultipartFile = new MockMultipartFile("user-image", "test-image.png",
            "image/png", "test-image.png".getBytes());
        
        InputStream inputStream = new ByteArrayInputStream("chicken".getBytes(Charset.forName("UTF-8")));
        Image expectedImage = new Image();
        expectedImage.setFileName("test-image.png");
        expectedImage.setFileData("imageData");

        Mockito.when(metadataService.save(expectedImage)).thenReturn(expectedImage);
        doNothing().when(fileStorageService).save("test-image.png", inputStream);

        Image actualImage = imageService.save(mockMultipartFile);

        assertEquals(expectedImage.getFileName(), actualImage.getFileName());

    }
}