package com.exelaration.abstractmemery.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.exelaration.abstractmemery.domains.Image;

@ExtendWith(MockitoExtension.class)
public class ImageServiceTest {

    @Mock 
    private FileStorageService fileStorageService;

    @Mock 
    private MetadataService metadataService;
    
    @InjectMocks
    private ImageService imageService;

    @Test
    public void testSaveImageSuccessfully() {
        //look into moving this stuff into a set up method with @before annotation
        MockMultipartFile mockMultipartFile = new MockMultipartFile("user-image", "test-image",
            "image/png", "imageData".getBytes());

        Image mockImage = new Image();
        mockImage.setFileName("user-image");
        mockImage.setFileData("imageData");
        mockImage.setFileLocation("fileLocation");

        Mockito.when(metadataService.save(mockImage)).thenReturn(mockImage);
        Mockito.when(fileStorageService.save("user-image", mockMultipartFile.getInputStream())).thenReturn("fileLocation");

        Image testImage = imageService.save(mockMultipartFile);

        assertThat(testImage).isSameAs(mockImage);

    }
}