package com.exelaration.abstractmemery.services.implementations;

import static java.util.Collections.emptyList;

import com.exelaration.abstractmemery.domains.ApplicationUser;
import com.exelaration.abstractmemery.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
  @Autowired private UserRepository userRepository;

  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public ApplicationUser saveUser(ApplicationUser user) {
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    try {
      return userRepository.save(user);
    } catch (Exception e) {
      return null;
    }
  }

  public boolean userExists(String username) {
    if (userRepository.findByUsername(username) == null) {
      return false;
    }
    return true;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    ApplicationUser applicationUser = userRepository.findByUsername(username);
    if (applicationUser == null) {
      throw new UsernameNotFoundException(username);
    }
    return new User(applicationUser.getUsername(), applicationUser.getPassword(), emptyList());
  }
}
