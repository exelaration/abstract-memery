package com.exelaration.abstractmemery.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import com.exelaration.abstractmemery.domains.ApplicationUser;
import com.exelaration.abstractmemery.repositories.UserRepository;
import com.exelaration.abstractmemery.services.implementations.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class UserDetailsServiceTest {
  @Mock private BCryptPasswordEncoder BCryptPasswordEncoder;

  @Mock private UserRepository userRepository;

  @InjectMocks private UserDetailsServiceImpl userDetailsService;

  @BeforeEach
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void saveUser_WhenUserSavesSuccessfully_ExpectUser() {
    ApplicationUser expectedUser = new ApplicationUser();
    expectedUser.setUsername("admin");
    expectedUser.setPassword("password");

    Mockito.when(userRepository.save(Mockito.any())).thenReturn(expectedUser);

    ApplicationUser actualUser = userDetailsService.saveUser(expectedUser);
    expectedUser.setPassword(BCryptPasswordEncoder.encode(expectedUser.getPassword()));
    assertEquals(expectedUser, actualUser);
  }

  @Test
  public void saveUser_WhenUserSaveUnsuccessful_ExpectNull() {
    ApplicationUser user = new ApplicationUser();
    Mockito.when(userRepository.save(Mockito.any())).thenThrow(new IllegalArgumentException());
    assertNull(userDetailsService.saveUser(user));
    verify(BCryptPasswordEncoder).encode(Mockito.eq(user.getPassword()));
  }

  @Test
  public void loadUserByUsername_WhenGivenValidUsername_ExpectUser() {
    ApplicationUser expectedUser = new ApplicationUser();
    expectedUser.setUsername("admin");
    expectedUser.setPassword("password");

    Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(expectedUser);
    UserDetails actualUser = userDetailsService.loadUserByUsername(expectedUser.getUsername());
    assertEquals(expectedUser.getUsername(), actualUser.getUsername());
  }

  @Test
  public void loadUserByUsername_WhenUserNotFound_ExpectUserNotFoundException() {
    Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(null);
    assertThrows(
        UsernameNotFoundException.class,
        () -> {
          userDetailsService.loadUserByUsername("admin");
        });
  }
}
