package com.exelaration.abstractmemery.controllers;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.exelaration.abstractmemery.domains.Image;
import com.exelaration.abstractmemery.services.implementations.ImageServiceImpl;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

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

  @Test
  public void getImagesForUpload_WhenImageExists_ExpectStatus200andJSONreturn() throws Exception {
    ResultMatcher ok = MockMvcResultMatchers.status().isOk();
    String url = "/upload";
    ArrayList<Image> expectedImages = new ArrayList<Image>();
    Image testImage = new Image();
    testImage.setFileName("testImage");
    expectedImages.add(testImage);

    when(imageService.getImages()).thenReturn(expectedImages);

    mockMvc
        .perform(MockMvcRequestBuilders.get(url))
        .andExpect(ok)
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].fileName", is("testImage")));
  }

  @Test
  public void getImagesForUpload_WhenImageIsNull_ExpectStatus404andJSONreturn() throws Exception {
    ResultMatcher notFound = MockMvcResultMatchers.status().isNotFound();
    String url = "/upload";

    when(imageService.getImages())
        .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Images Do Not Exist"));

    mockMvc.perform(MockMvcRequestBuilders.get(url)).andExpect(notFound);
  }
}
