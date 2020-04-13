package com.exelaration.abstractmemery.controllers;

import static org.mockito.Mockito.when;

import com.exelaration.abstractmemery.domains.Image;
import com.exelaration.abstractmemery.services.implementations.ImageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UploadController.class)
public class UploadControllerTest {

  private MockMvc mockMvc;
  @Autowired private UploadController uploadController;
  @MockBean private ImageServiceImpl imageService;

  @BeforeEach
  public void setUp() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(uploadController).build();
  }

  @Test
  public void saveImage_WhenImageIsProvided_Expect200() throws Exception {
    ResultMatcher ok = MockMvcResultMatchers.status().isOk();
    String url = "/upload/";
    String fileName = "test.jpg";
    MockMultipartFile imageFile =
        new MockMultipartFile("file", fileName, "image/jpeg", "test image content".getBytes());
    Image testImage = new Image();

    when(imageService.save(imageFile)).thenReturn(testImage);
    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart(url).file(imageFile);

    mockMvc.perform(builder).andExpect(ok);
  }

  @Test
  public void saveImage_WhenImageIsNull_Expect400() throws Exception {
    ResultMatcher badRequest = MockMvcResultMatchers.status().isBadRequest();
    String url = "/upload/";
    MockMultipartFile imageFile = null;
    Image testImage = null;

    when(imageService.save(imageFile)).thenReturn(testImage);
    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart(url);

    mockMvc.perform(builder).andExpect(badRequest);
  }
}
