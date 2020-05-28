package com.exelaration.abstractmemery.controllers;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.exelaration.abstractmemery.domains.ApplicationUser;
import com.exelaration.abstractmemery.services.UserService;
import com.exelaration.abstractmemery.services.implementations.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
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
@ContextConfiguration(classes = UserController.class)
public class UserControllerTest {
  private MockMvc mockMvc;
  @Autowired private UserController userController;
  @MockBean private UserDetailsServiceImpl userDetailsService;
  @MockBean private UserService userService;

  @BeforeEach
  public void setUp() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
  }

  @Test
  public void UserControllerTest_WhenNewUserRegisters_ExpectStatus200() throws Exception {
    ResultMatcher ok = MockMvcResultMatchers.status().isOk();
    String url = "/users/sign-up";
    String expectedMessage = "User has been created";

    ApplicationUser testUser = new ApplicationUser();
    testUser.setUsername("testUser@gmail.com");
    testUser.setPassword("ABC123abc!");

    when(userDetailsService.saveUser(any())).thenReturn(testUser);
    when(userDetailsService.userExists(testUser.getUsername())).thenReturn(false);

    assertEquals(expectedMessage, userController.signUp(testUser));
    mockMvc
        .perform(
            MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(testUser)))
        .andExpect(ok);
  }

  @Test
  public void UserControllerTest_WhenExistingUserRegisters_ExpectStatus200() throws Exception {
    ResultMatcher ok = MockMvcResultMatchers.status().isOk();
    String url = "/users/sign-up";
    String expectedMessage = "User already exists";

    ApplicationUser testUser = new ApplicationUser();
    testUser.setUsername("testUser@gmail.com");
    testUser.setPassword("ABC123abc!");

    when(userDetailsService.saveUser(any())).thenReturn(testUser);
    when(userDetailsService.userExists(testUser.getUsername())).thenReturn(true);

    assertEquals(expectedMessage, userController.signUp(testUser));
    mockMvc
        .perform(
            MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(testUser)))
        .andExpect(ok);
  }

  @Test
  public void UserControllerTest_WhenRegisterFails_ExpectStatus400() throws Exception {
    ResultMatcher badRequest = MockMvcResultMatchers.status().isBadRequest();
    String url = "/users/sign-up";

    ApplicationUser testUser = null;

    when(userDetailsService.saveUser(any())).thenReturn(testUser);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(testUser)))
        .andExpect(badRequest);
  }

  @Test
  public void getUsersForSearch_WhenUsersExist_Expect200andArray() throws Exception {
    ResultMatcher ok = MockMvcResultMatchers.status().isOk();
    String url = "/users/?text=test";

    ArrayList<String> expectedUsernames = new ArrayList<String>();
    expectedUsernames.add("test@gmail.com");
    when(userService.getUsersWithText(any())).thenReturn(expectedUsernames);

    mockMvc
        .perform(MockMvcRequestBuilders.get(url))
        .andExpect(ok)
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0]", is("test@gmail.com")));
  }

  @Test
  public void getUsersForSearch_WhenUsersDoesNotExist_Expect200andEmptyArray() throws Exception {
    ResultMatcher ok = MockMvcResultMatchers.status().isOk();
    String url = "/users/?text=test";

    ArrayList<String> expectedUsernames = new ArrayList<String>();
    when(userService.getUsersWithText(any())).thenReturn(expectedUsernames);

    mockMvc
        .perform(MockMvcRequestBuilders.get(url))
        .andExpect(ok)
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$", hasSize(0)));
  }
}
