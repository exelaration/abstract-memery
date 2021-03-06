package com.exelaration.abstractmemery.controllers;

import com.exelaration.abstractmemery.domains.ApplicationUser;
import com.exelaration.abstractmemery.services.implementations.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired private UserDetailsServiceImpl userDetailsService;

  @PostMapping("/sign-up")
  public String signUp(@RequestBody ApplicationUser user) {
    if (!userDetailsService.userExists(user.getUsername())) {
      userDetailsService.saveUser(user);
      return "User has been created";
    }
    return "User already exists";
  }

  @PutMapping("/settings")
  public String updateSettings(@RequestBody ApplicationUser user) {
    if (!userDetailsService.userExists(user.getUsername())) {
      userDetailsService.saveUser(user);
      return "User has been updated";
    }
    return "User already exists";
  }
}
