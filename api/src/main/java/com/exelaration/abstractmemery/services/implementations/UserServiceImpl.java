package com.exelaration.abstractmemery.services.implementations;

import com.exelaration.abstractmemery.projections.UserProjection;
import com.exelaration.abstractmemery.repositories.UserRepository;
import com.exelaration.abstractmemery.services.UserService;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

  @Autowired private UserRepository userRepository;

  public ArrayList<String> getUsersWithText(String username) {
    ArrayList<UserProjection> usernamesProjections =
        userRepository.findByUsernameIgnoreCaseContains(username);
    ArrayList<String> usernames = new ArrayList<String>();
    for (UserProjection name : usernamesProjections) {
      usernames.add(name.getUsername());
    }
    return usernames;
  }
}
