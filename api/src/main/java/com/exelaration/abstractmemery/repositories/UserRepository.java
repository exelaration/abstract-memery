package com.exelaration.abstractmemery.repositories;

import com.exelaration.abstractmemery.domains.ApplicationUser;
import com.exelaration.abstractmemery.projections.UserProjection;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<ApplicationUser, Integer> {
  ApplicationUser findByUsername(String username);

  ArrayList<UserProjection> findByUsernameIgnoreCaseContains(String username);
}
