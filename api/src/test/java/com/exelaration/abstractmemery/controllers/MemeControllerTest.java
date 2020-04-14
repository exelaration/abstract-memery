package com.exelaration.abstractmemery.controllers;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.exelaration.abstractmemery.domains.Meme;
import com.exelaration.abstractmemery.repositories.MemeRepository;
import com.exelaration.abstractmemery.services.implementations.MemeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MemeController.class)
public class MemeControllerTest {
  private MockMvc mockMvc;
  @Autowired MemeController memeController;
  @MockBean private MemeServiceImpl memeService;

  @MockBean private MemeRepository memeRepository;

  @BeforeEach
  public void setUp() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(memeController).build();
  }

  @Test
  public void uploadMeme_WhenMemePosts_ExpectStatus200() throws Exception {
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
  public void uploadMeme_WhenNullMemePosts_ExpectStatus400() throws Exception {
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

  @Test
  public void getMeme_WhenImageIsPresent_ExpectStatus200() throws Exception {
    ResultMatcher ok = MockMvcResultMatchers.status().isOk();
    String url = "/meme/?id=3";
    String meme = "meme data";

    when(memeService.getMeme(3)).thenReturn(meme);

    mockMvc.perform(MockMvcRequestBuilders.get(url)).andExpect(ok);
  }

  @Test
  public void getMeme_WhenImageDoesNotExist_ExpectStatus404() throws Exception {
    ResultMatcher notFound = MockMvcResultMatchers.status().isNotFound();
    String url = "/meme/?id=3";

    when(memeService.getMeme(3))
        .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find image"));

    mockMvc.perform(MockMvcRequestBuilders.get(url)).andExpect(notFound);
  }

  @Test
  public void getMeme_WhenImageCannotBeFoundLocally_ExpectStatus200() throws Exception {
    ResultMatcher ok = MockMvcResultMatchers.status().isOk();
    String url = "/meme/?id=3";
    String meme = "";

    when(memeService.getMeme(3)).thenReturn(meme);

    mockMvc.perform(MockMvcRequestBuilders.get(url)).andExpect(ok);
  }

  public void getMemesForGallery_WhenMemeExists_ExpectStatus200andJSONreturn() throws Exception {
    ResultMatcher ok = MockMvcResultMatchers.status().isOk();
    String url = "/meme";
    ArrayList<Meme> expectedMemes = new ArrayList<Meme>();
    Meme testMeme = new Meme();
    testMeme.setMemeName("testMeme");
    expectedMemes.add(testMeme);

    when(memeService.getMemes()).thenReturn(expectedMemes);

    mockMvc
        .perform(MockMvcRequestBuilders.get(url))
        .andExpect(ok)
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].memeName", is("testMeme")));
  }

  @Test
  public void getMemesForGallery_WhenMemeIsNull_ExpectStatus404andJSONreturn() throws Exception {
    ResultMatcher notFound = MockMvcResultMatchers.status().isNotFound();
    String url = "/meme";

    when(memeService.getMemes())
        .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Images Do Not Exist"));

    mockMvc.perform(MockMvcRequestBuilders.get(url)).andExpect(notFound);
  }
}
