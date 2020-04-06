package com.exelaration.abstractmemery.repositories;

import com.exelaration.abstractmemery.domains.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<ApplicationUser, Integer> {
  ApplicationUser findByUsername(String username);
}
