package com.exelaration.abstractmemery.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.exelaration.abstractmemery.domains.Meme;
import com.exelaration.abstractmemery.services.implementations.MemeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MemeController.class)
public class MemeControllerTest {
  private MockMvc mockMvc;
  @Autowired MemeController memeController;
  @MockBean private MemeServiceImpl memeService;

  @BeforeEach
  public void setUp() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(memeController).build();
  }

  @Test
  public void MemeControllerTest_WhenMemePosts_ExpectStatus200() throws Exception {
    ResultMatcher ok = MockMvcResultMatchers.status().isOk();
    String url = "/meme/";

    Meme testMeme = new Meme();

    when(memeService.save(any())).thenReturn(testMeme);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(testMeme)))
        .andExpect(ok);
  }

  @Test
  public void MemeControllerTest_WhenNullMemePosts_ExpectStatus400() throws Exception {
    ResultMatcher badRequest = MockMvcResultMatchers.status().isBadRequest();
    String url = "/meme/";

    Meme testMeme = null;

    when(memeService.save(any())).thenReturn(testMeme);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(testMeme)))
        .andExpect(badRequest);
  }
}
