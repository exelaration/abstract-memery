package com.exelaration.abstractmemery.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
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
import org.mockito.exceptions.base.MockitoException;
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
    public void imageServiceSave_WhenImageSavedSuccessfully_ExpectImageReturned() throws IOException {
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
    @Test
    public void imageServiceSave_WhenMetadataSaveUnsuccessful_ExpectError() throws IOException {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("user-image", "test-image.png",
            "image/png", "test-image.png".getBytes());
        
        Mockito.when(metadataService.save(null)).thenThrow(new IllegalArgumentException());
        imageService.save(mockMultipartFile);
        assertThrows(IllegalArgumentException.class, () -> metadataService.save(null));
    }
    @Test
    public void imageServiceSave_WhenFileStorageSaveUnsuccessful_ExpectRuntimeException() throws IOException {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("user-image", "test-image.png",
            "image/png", "test-image.png".getBytes());

        doThrow(new RuntimeException()).when(fileStorageService).save(null, null);
        imageService.save(mockMultipartFile);
        assertThrows(RuntimeException.class, () -> fileStorageService.save(null, null));
    }
    @Test
    public void imageServiceSave_WhenFileStorageSaveUnsuccessful_ExpectIOException() throws IOException {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("user-image", "test-image.png",
            "image/png", "test-image.png".getBytes());
    
        doThrow(new IOException()).when(fileStorageService).save(Mockito.eq("test-image.png"), Mockito.eq(null));
        imageService.save(mockMultipartFile);
        assertThrows(IOException.class, () -> fileStorageService.save(Mockito.eq("test-image.png"), null));
    }
    @Test
    public void imageServiceSave_WhenInvalidFile_ExpectIOException() throws IOException {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("user-image", "test-image.png",
            "image/png", "test-image.png".getBytes());
        
        doThrow(new Exception()).when(mockMultipartFile).getBytes();
        assertThrows(Exception.class, () -> imageService.save(mockMultipartFile));
    }
}