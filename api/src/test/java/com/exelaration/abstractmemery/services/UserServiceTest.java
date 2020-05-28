package com.exelaration.abstractmemery.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.exelaration.abstractmemery.projections.UserProjection;
import com.exelaration.abstractmemery.repositories.UserRepository;
import com.exelaration.abstractmemery.services.implementations.UserServiceImpl;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {
  @Mock private UserRepository userRepository;
  @InjectMocks private UserServiceImpl userService;

  @BeforeEach
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void getUsersWithText_WhenUsersExist_ExpectArray() {
    ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
    UserProjection userProjection = factory.createProjection(UserProjection.class);
    userProjection.setUsername("test@gmail.com");
    ArrayList<UserProjection> returnedUsernames = new ArrayList<UserProjection>();
    returnedUsernames.add(userProjection);
    Mockito.when(userRepository.findByUsernameIgnoreCaseContains("test"))
        .thenReturn(returnedUsernames);

    ArrayList<String> expectedUsernames = new ArrayList<String>();
    expectedUsernames.add("test@gmail.com");
    assertEquals(expectedUsernames, userService.getUsersWithText("test"));
  }

  @Test
  public void getUsersWithText_WhenUsersDoesNotExist_ExpectArray() {
    ArrayList<UserProjection> returnedUsernames = new ArrayList<UserProjection>();
    Mockito.when(userRepository.findByUsernameIgnoreCaseContains("test"))
        .thenReturn(returnedUsernames);
    ArrayList<String> expectedUsernames = new ArrayList<String>();
    assertEquals(expectedUsernames, userService.getUsersWithText("test"));
  }
}
